import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable} from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ExtractorItem } from './extractor.model';
import { CV, NewCV } from '../cv/cv.model';
import { ExtractorService } from './extractor.service';
import { CVService } from '../cv/cv.service';

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
        private controlvarService: CVService,
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
        const newCV: NewCV = new NewCV();
        newCV.extractorClass = this.controlvar.extractor.extractorClass;
        newCV.controlVariable = this.controlvar;
        this.getFieldsExtractorToInclude().forEach((x) => (newCV.extractorData[x] = this.controlvar.extractor[x]));
        this.subscribeToSaveResponse(
            this.controlvarService.create(newCV));
    }

    private subscribeToSaveResponse(result: Observable<NewCV>) {
        result.subscribe(
            (res: NewCV) => (this.onSaveSuccess(res)),
            (res: Response) => (this.onSaveError(res)));
    }

    private onSaveSuccess(result: NewCV) {
        console.log('OK!');
        // this.eventManager.broadcast({ name: 'acmeListModification', content: 'OK'});
        // this.isSaving = false;
        // this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        // this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    cancel() {
    }
}
