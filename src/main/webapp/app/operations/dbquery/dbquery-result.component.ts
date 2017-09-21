import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { DBResponse } from './dbquery.model'
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-dbquery-list',
    templateUrl: './dbquery-result.component.html'
})
export class DBQueryResultComponent implements OnInit, OnDestroy {

    @Input()
    dbResponse: DBResponse;

    constructor(
    ) {}

    ngOnInit() {}

    ngOnDestroy() {}

    fields() {
        return this.dbResponse.metadata.fields;
    }
}
