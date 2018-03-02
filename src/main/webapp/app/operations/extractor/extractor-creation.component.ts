import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ExtractorItem } from './extractor.model';
import { CV, NewCV } from '../cv/cv.model';
import { ExtractorService } from './extractor.service';

import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-extractor-creation',
    templateUrl: './extractor-creation.component.html',
    styleUrls: ['./extractor-creation.component.css']
})
export class ExtractorCreationComponent implements OnInit, OnDestroy {

    controlvar: CV;
    currentAccount: any;
    cvFields: String[];
    extractorFields: String[];

    constructor(
        private extractorService: ExtractorService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private router: ActivatedRoute
    ) {
        this.controlvar = new CV();
    }

    loadExtractor() {
        const uid = this.router.snapshot.paramMap.get('uid');
        this.extractorService.find(uid).subscribe((extractor) => {
            this.controlvar.extractor = extractor;
            this.cvFields = this.getFieldsCVToInclude();
            this.extractorFields = this.getFieldsExtractorToInclude();
            console.log(this.controlvar);
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
        return JSON.stringify(this.controlvar, undefined, 2);
    }

    getFieldsCVToExclude() {
        return ['id', 'status', 'creationMoment', 'controlVarEntries', 'extractor'];
    }
    getFieldsExtractorToExclude() {
        return ['extractorClass', 'style', 'uid'];
    }

    getFieldsCVToInclude() {
        return Object.keys(this.controlvar).filter((x) => this.getFieldsCVToExclude().indexOf(x) === -1);
    }

    getFieldsExtractorToInclude() {
        return Object.keys(this.controlvar.extractor).filter((x) => this.getFieldsExtractorToExclude().indexOf(x) === -1);
    }

    save() {
        // var newCV: NewCV = new NewCV();
        // newCV.extractorClass = this.controlvar.extractor.extractorClass;
        // newCV.controlVariable = this.controlvar;
        // this.getFieldsExtractorToInclude()
        console.log(this.controlvar);
    }

    cancel() {
    }
}
