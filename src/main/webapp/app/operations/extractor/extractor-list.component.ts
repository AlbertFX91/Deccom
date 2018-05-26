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
    extractorGroups: any;
    groupSelected: string;

    constructor(
        private extractorService: ExtractorService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {
        this.extractors = [];
        this.extractorSelected = null;
        this.extractorGroups = {};
        this.groupSelected = null;
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

    getGroups() {
        return Object.keys(this.extractorGroups);
    }
    selectGroup(icon) {
        if (this.groupSelected === icon) {
            this.groupSelected = null;
        } else {
            this.groupSelected = icon;
        }
    }

    getExtractors() {
        if (this.groupSelected === null) {
            return this.extractors;
        } else {
            return this.extractors.filter((e) => e.style.icon === this.groupSelected);
        }
    }

    private onSuccess(data, headers) {
        data.sort( (a, b) => {
            if (a.style.icon < b.style.icon) {
                return -1;
            }
            if (a.style.icon > b.style.icon) {
                return 1;
            }
            return a.style.name < b.style.name ? -1 : 1;
          });
        for (let i = 0; i < data.length; i++) {
            const extractor: ExtractorItem = data[i];
            this.extractors.push(extractor);

            if (!(extractor.style.icon in this.extractorGroups)) {
                this.extractorGroups[extractor.style.icon] = extractor.style;
            }
        }
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
