import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { SQLQuery, SQLResponse, SQLDataRecover } from './sql.model';
import { SQLService } from './sql.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-sql',
    templateUrl: './sql.component.html'
})
export class SQLComponent implements OnInit, OnDestroy {

    sqlQuery: SQLQuery = {};
    isSaving: boolean;
    result: any;
    sqlResponse: SQLResponse;
    sqlDataRecover: SQLDataRecover;
    constructor(
        private sqlQueryService: SQLService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.sqlDataRecover = {};
    }

    ngOnDestroy() {}

    sendQuery() {
        this.isSaving = true;
        this.sqlResponse = undefined;
        this.sqlDataRecover = {};
        this.sqlQueryService.query(this.sqlQuery).subscribe(
            (res: any) => this.onQuerySuccess(res),
            (error: Response) => this.onQueryError(error)
        )
    }

    onQuerySuccess(res: any) {
        this.isSaving = false;
        this.eventManager.broadcast({ name: 'sql_success', content: 'OK'});
        this.sqlResponse = res;
        this.sqlDataRecover.query = this.sqlQuery.query;
        this.sqlDataRecover.connection = {
            'username': this.sqlQuery.username,
            'password': this.sqlQuery.password,
            'url': this.sqlQuery.url
        }
    }

    onQueryError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    clear() {
        this.sqlQuery = {};
        this.sqlResponse = undefined;
        this.sqlDataRecover = {};
    }
    clearControlVar() {
        this.sqlDataRecover = {};
        this.sqlResponse = undefined;
    }

    constructQuery(selected: any) {
        const row: any = selected.row;
        const field = selected.field;
        const metadata = this.sqlResponse.metadata;
        let sql = this.sqlQuery.query.toLowerCase();
        // Removing from SELECT to FROM and adding the field name
        sql = sql.substr(0, sql.indexOf('select ') + 'select'.length)
            + ' '
            + field.name
            + ' '
            + sql.substr(sql.indexOf(' from '));

        // Adding WHERE plus primary keys if where doesnt exist on the SQL
        if (sql.indexOf(' where ') === -1) {
            let sqlpks = ' where ';
            metadata.fields
                .filter((f) => f.isPrimaryKey)
                .forEach((f, i) => {
                    sqlpks +=
                        (i > 0 ? ' and' : '')
                        + ' '
                        + f.name
                        + '='
                        + row['' + f.name];
                });
            sql = sql + sqlpks;
        }
        this.sqlDataRecover.query = sql;

    }

    sendControlVar() {
        this.isSaving = true;
        this.sqlQueryService.dataRecover(this.sqlDataRecover).subscribe(
            (res: any) => this.onDataRecoverSuccess(res),
            (error: Response) => this.onDataRecoverError(error)
        )
    }

    onDataRecoverSuccess(res: any) {
        console.log("NICE!");
        this.isSaving = false;
        this.clear();
    }

    onDataRecoverError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

}
