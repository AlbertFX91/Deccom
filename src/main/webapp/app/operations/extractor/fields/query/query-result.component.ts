import { Component, OnInit, OnDestroy, Input, EventEmitter, Output } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { SQLResponse, SQLField } from './query.model'
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../../../shared';

@Component({
    selector: 'jhi-sql-list',
    templateUrl: './query-result.component.html',
    styleUrls: [
        'query-result.css'
    ],
})
export class FieldSQLQueryResultComponent implements OnInit, OnDestroy {

    @Input()
    sqlResponse: SQLResponse;

    @Output()
    private selected = new EventEmitter<any>();

    fieldSelected: any = {};

    constructor(
    ) {}

    ngOnInit() {}

    ngOnDestroy() {}

    fields() {
        return this.sqlResponse.metadata.fields;
    }

    onFieldClick(row: any, field: SQLField) {
        this.fieldSelected.row = row;
        this.fieldSelected.field = field;
        this.selected.emit(this.fieldSelected);
    }

    isFieldSelected(row: any, field: SQLField) {
        return this.fieldSelected.row === row &&
            this.fieldSelected.field === field;
    }
}
