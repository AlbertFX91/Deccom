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
    data: any;
    path: string;
    itemsPerPage: number;
    links: any;
    page: any;

    constructor(
        private restcallsService: RestCallsService,
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
    }

    loadPage(page) {
        this.page = page;
        this.save();
    }

    ngOnInit() { }

    ngOnDestroy() { }

    onSubmit() {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.data = [];
        this.save();
    }

    save() {
        this.isSaving = true;
        const pageSettings = {
            page: this.page,
            size: this.itemsPerPage
        };
        this.restcallsService.noMapping(this.url, pageSettings).subscribe(
            (data: any) => this.onSuccess(data.json(), data.headers),
            (error: Response) => this.onJSONError(error)
        )
    }

    onSuccess(data: any, headers: any) {
        this.isSaving = false;
        this.data = this.data.concat(data);
        this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'nomapping_success', content: 'OK' });
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
        this.data = {};
        this.path = '';
    }

    createPath(path: string) {
        this.path = path;
    }

}
