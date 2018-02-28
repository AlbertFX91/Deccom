import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ExtractorItem } from './extractor.model';
import { ExtractorService } from './extractor.service';

import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-extractor-creation',
    templateUrl: './extractor-creation.component.html',
    styleUrls: ['./extractor-creation.component.css']
})
export class ExtractorCreationComponent implements OnInit, OnDestroy {

    extractor: ExtractorItem;
    currentAccount: any;

    constructor(
        private extractorService: ExtractorService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private router: ActivatedRoute
    ) {
        this.extractor = null;
    }

    loadExtractor() {
        const uid = this.router.snapshot.paramMap.get('uid');
        console.log(uid);
        this.extractorService.find(uid).subscribe((extractor) => {
            this.extractor = extractor;
        });
    }

    ngOnInit() {
        this.loadExtractor();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
    }

    ngOnDestroy() {}

    printExtractor() {
        return JSON.stringify(this.extractor, undefined, 2);
    }
}
