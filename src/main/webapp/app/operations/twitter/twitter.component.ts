import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { RESTDataRecover, RESTConnection } from '../rest/rest.model';
import { RESTService } from '../rest/rest.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-twitter',
    templateUrl: './twitter.component.html'
}) export class TwitterComponent implements OnInit, OnDestroy {

    isSaving: boolean;
    url: string;
    result: any;
    data: any;
    path: string;
    itemsPerPage: number;
    links: any;
    page: any;
    restDataRecover: RESTDataRecover;

    constructor(
        private restService: RESTService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.data = [];
        this.path = '';
        this.url = 'https://api.twitter.com/1.1/users/lookup.json?screen_name=deccom2018';
    }

    loadPage(page) {
        this.page = page;
        this.save();
    }

    ngOnInit() {
        this.isSaving = false;
        this.restDataRecover = {};
    }

    ngOnDestroy() { }

    onSubmit() {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.data = [];
        this.path = '';
        this.save();
    }

    save() {
        this.isSaving = true;
        const pageSettings = {
            page: this.page,
            size: this.itemsPerPage
        };
        this.restService.noMapping(this.url, pageSettings).subscribe(
            (data: any) => this.onSuccess(data.json(), data.headers),
            (error: Response) => this.onJSONError(error)
        )
    }

    onSuccess(data: any, headers: any) {
        this.isSaving = false;
        this.data = this.data.concat(data);
        this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'nomapping_success', content: 'OK' });
        this.restDataRecover.restConnection = {
            'url': this.url
        }
    }

    createControlVar() {
        this.restDataRecover.query = this.path;
        this.isSaving = true;
        this.restService.restDataRecover(this.restDataRecover).subscribe(
            (res: any) => this.onRESTDataRecoverSuccess(res),
            (error: Response) => this.onJSONError(error)
        )
    }

    clearControlVar() {
        this.restDataRecover = {};
    }

    onRESTDataRecoverSuccess(res: any) {
        this.isSaving = false;
        this.eventManager.broadcast({ name: 'restdatarecover_success', content: 'OK' });
        this.clear();
    }

    onJSONError(error) {
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
        this.url = '';
        this.restDataRecover = {};
        this.page = 0;
        this.links = {
            last: 0
        };
        this.data = [];
        this.path = '';
    }

    createPath(path: string) {
        this.path = path;
    }

}
