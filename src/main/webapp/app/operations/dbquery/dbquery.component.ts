import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { DBQuery } from './dbquery.model';
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
    queryResult: any;
    constructor(
        private dbQueryService: DBQueryService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {}

    ngOnInit() {
        this.isSaving = false;
    }

    ngOnDestroy() {}

    save() {
        this.isSaving = true;
        this.queryResult = undefined;
        this.dbQueryService.query(this.dbQuery).subscribe(
            (res: any) => this.onQuerySuccess(res),
            (error: Response) => this.onQueryError(error)
        )
    }

    onQuerySuccess(res: any) {
        this.isSaving = false;
        this.eventManager.broadcast({ name: 'dbquery_success', content: 'OK'});

        // It's not a list, its only a element
        if (res.length === undefined) {
            this.queryResult = res;
        } else {
            // If the list has only 1 element, we get only the element. If the list has more than 1 element, we get all the elements
            this.queryResult = res.length === 1 ? res[0] : res
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
        this.dbQuery = {};
        this.queryResult = undefined;
    }

}
