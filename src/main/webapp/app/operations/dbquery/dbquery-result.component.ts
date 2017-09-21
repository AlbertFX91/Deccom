import { Component, OnInit, OnDestroy, Input } from '@angular/core';
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

    fieldSelected: any = {};

    constructor(
    ) {}

    ngOnInit() {}

    ngOnDestroy() {}

    fields() {
        return this.dbResponse.metadata.fields;
    }

    onFieldClick(index: number, field: DBField) {
        this.fieldSelected.index = index;
        this.fieldSelected.field = field;
    }

    isFieldSelected(index: number, field: DBField) {
        return this.fieldSelected.index === index &&
            this.fieldSelected.field === field;
    }
}
