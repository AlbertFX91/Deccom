import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { DBQuery, DBResponse } from './dbquery.model';
import { DBQueryService } from './dbquery.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-dbquery',
    templateUrl: './dbquery.component.html'
})
export class DBQueryComponent implements OnInit, OnDestroy {

    dbQuery: DBQuery = {};
    isSaving: boolean;
    result: any;
    dbResponse: DBResponse;
    sqlQueryResult: string;
    constructor(
        private dbQueryService: DBQueryService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.sqlQueryResult = '';
    }

    ngOnDestroy() {}

    save() {
        this.isSaving = true;
        this.dbResponse = undefined;
        this.sqlQueryResult = '';
        this.dbQueryService.query(this.dbQuery).subscribe(
            (res: any) => this.onQuerySuccess(res),
            (error: Response) => this.onQueryError(error)
        )
    }

    onQuerySuccess(res: any) {
        this.isSaving = false;
        this.eventManager.broadcast({ name: 'dbquery_success', content: 'OK'});
        this.dbResponse = res;
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
        this.dbQuery = {};
        this.dbResponse = undefined;
        this.sqlQueryResult = '';
    }

    constructQuery(selected: any) {
        console.log(selected);
        const row: any = selected.row;
        const field = selected.field;
        const metadata = this.dbResponse.metadata;
        let sql = this.dbQuery.query.toLowerCase();
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
        this.sqlQueryResult = sql;

    }

}
