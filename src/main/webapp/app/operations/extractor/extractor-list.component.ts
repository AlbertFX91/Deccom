import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ExtractorItem } from './extractor.model';
import { ExtractorService } from './extractor.service';

import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-extractor-list',
    templateUrl: './extractor-list.component.html',
    styleUrls: ['./extractor-list.component.css']
})
export class ExtractorListComponent implements OnInit, OnDestroy {

    extractorSelected: ExtractorItem;
    extractors: ExtractorItem[];
    currentAccount: any;

    constructor(
        private extractorService: ExtractorService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {
        this.extractors = [];
        this.extractorSelected = null;
    }

    loadAll() {
        this.extractorService.all().subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
    }

    ngOnDestroy() {}

    onExtractorClick(extractor: ExtractorItem) {
        this.extractorSelected = extractor;
    }

    private onSuccess(data, headers) {
        for (let i = 0; i < data.length; i++) {
            this.extractors.push(data[i]);
        }
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
