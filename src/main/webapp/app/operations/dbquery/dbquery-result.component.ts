import { Component, OnInit, OnDestroy, Input, EventEmitter, Output } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { DBResponse, DBField } from './dbquery.model'
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-dbquery-list',
    templateUrl: './dbquery-result.component.html',
    styleUrls: [
        'dbquery-result.css'
    ],
})
export class DBQueryResultComponent implements OnInit, OnDestroy {

    @Input()
    dbResponse: DBResponse;

    @Output()
    private selected = new EventEmitter<any>();

    fieldSelected: any = {};

    constructor(
    ) {}

    ngOnInit() {}

    ngOnDestroy() {}

    fields() {
        return this.dbResponse.metadata.fields;
    }

    onFieldClick(row: any, field: DBField) {
        this.fieldSelected.row = row;
        this.fieldSelected.field = field;
        console.log(this.fieldSelected);
        this.selected.emit(this.fieldSelected);
    }

    isFieldSelected(row: any, field: DBField) {
        return this.fieldSelected.row === row &&
            this.fieldSelected.field === field;
    }
}
