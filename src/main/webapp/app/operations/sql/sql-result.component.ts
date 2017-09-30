import { Component, OnInit, OnDestroy, Input, EventEmitter, Output } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { SQLResponse, SQLField } from './sql.model'
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sql-list',
    templateUrl: './sql-result.component.html',
    styleUrls: [
        'sql-result.css'
    ],
})
export class SQLResultComponent implements OnInit, OnDestroy {

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
