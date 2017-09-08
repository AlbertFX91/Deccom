import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { RestCallsService } from './restcalls.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-restcalls',
    templateUrl: './restcalls.component.html'
}) export class RestCallsComponent implements OnInit, OnDestroy {

    isSaving: boolean;
    url: string;
    result: any;
    response: any;

    constructor(
        private restcallsService: RestCallsService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) { }

    ngOnInit() { }

    ngOnDestroy() { }

    save() {
        this.isSaving = true;
        this.restcallsService.noMapping(this.url).subscribe(
            (data: any) => this.onSuccess(data),
            (error: Response) => this.onErrorJSON(error)
        )
    }

    onSuccess(data: any) {
        this.isSaving = false;
        this.response = data;
        this.eventManager.broadcast({ name: 'nomapping_success', content: 'OK' });
    }

    onErrorJSON(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error('deccomApp.apiRESTCalls.wrongURL', null, null);
    }

    clear() {
        this.url = '';
        this.response = {};
    }

}
