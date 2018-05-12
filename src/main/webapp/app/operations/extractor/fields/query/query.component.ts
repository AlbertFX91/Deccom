import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { SQLQuery, SQLResponse, SQLDataRecover } from './query.model';
import { SQLQueryService } from './query.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../../../shared';
import { PaginationConfig } from '../../../../blocks/config/uib-pagination.config';
import { FieldBaseComponent } from '../fields.component';

@Component({
    selector: 'jhi-sql-query',
    templateUrl: './query.component.html'
})
export class FieldSQLQueryComponent extends FieldBaseComponent implements OnInit, OnDestroy {

    sqlQuery: SQLQuery = {};
    isSaving: boolean;
    result: any;
    sqlResponse: SQLResponse;
    sqlDataRecover: SQLDataRecover;
    canSave: boolean;
    constructor(
        private sqlQueryService: SQLQueryService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {
        super();
    }

    ngOnInit() {
        this.isSaving = false;
        this.canSave = false;
        this.sqlDataRecover = {};

        this.sqlQuery.username = this.getValue('username');
        this.sqlQuery.password = this.getValue('password');
        this.sqlQuery.url = this.getValue('url');
        this.sqlQuery.jdbc = this.getValue('jdbc');
    }

    ngOnDestroy() { }

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
        this.eventManager.broadcast({ name: 'sql_success', content: 'OK' });
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
            + sql.substr(sql.indexOf(' from '));

        // Adding  primary keys if wheres exist in the query
        if (sql.indexOf(' where ') > -1) {
            let sqlpks = '';
            metadata.fields
                .filter((f) => f.isPrimaryKey)
                .filter((f) => sql.indexOf('' + f.name) === -1)
                .forEach((f, i) => {
                    sqlpks += ' and'
                        + ' '
                        + f.name
                        + '='
                        // Always as string attributes
                        + '\'' + row['' + f.name] + '\'';
                });
            sql = sql + sqlpks;
        } else {
            // Adding WHERE plus primary keys if where doesnt exist in the query
            let sqlpks = ' where';
            metadata.fields
                .filter((f) => f.isPrimaryKey)
                .forEach((f, i) => {
                    sqlpks +=
                        (i > 0 ? ' and' : '')
                        + ' '
                        + f.name
                        + '='
                        // Always as string attributes
                        + '\'' + row['' + f.name] + '\'';
                });
            sql = sql + sqlpks;
        }
        this.sqlDataRecover.query = sql;
        // Check if the value is a integer
        const name = selected.field.name;
        const val = selected.row[name];
        // If is a number
        this.canSave = !isNaN(val);

    }

    onDataRecoverSuccess(res: any) {
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

    onClose() {
        if (this.sqlDataRecover.query) {
          this.setValue(this.sqlDataRecover.query);
          this.setValueByAttr('url', this.sqlDataRecover.connection.url);
          this.setValueByAttr('username', this.sqlDataRecover.connection.username);
          this.setValueByAttr('password', this.sqlDataRecover.connection.password);
          this.finish();
        }
      }

}
