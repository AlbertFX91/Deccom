import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-dbquery-list',
    templateUrl: './dbquery-result.component.html'
})
export class DBQueryResultComponent implements OnInit, OnDestroy {

    @Input()
    queryResult: any;
    predicate: any;

    constructor(
    ) {}

    ngOnInit() {}

    ngOnDestroy() {}

    list() {
        return this.queryResult.length > 1;
    }

    detail() {
        return this.queryResult.length === undefined;
    }

    properties() {
        // If its a list, we get one element from the list and we get all the keys
        // After get it, we sort it to get always the same order of the keys.
        if (this.list()) {
            return Object.keys(this.queryResult[0]).sort();
        } else {
            return Object.keys(this.queryResult).sort();
        }
    }
}
