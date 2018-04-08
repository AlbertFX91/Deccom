import { Component, OnInit, OnDestroy, ViewChild, ComponentFactoryResolver } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable} from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ExtractorItem, DeccomField } from './extractor.model';
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
    extractorFields: DeccomField[];
    extractorDisableFields: DeccomField[];
    isSaving: boolean;

    currentField: DeccomField;

    constructor(
        private extractorService: ExtractorService,
        private controlvarService: CVService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private router: ActivatedRoute,
        private mainRouter: Router,
        private componentFactoryResolver: ComponentFactoryResolver,
    ) {
        this.controlvar = new CV();
        this.currentField = null;
    }

    loadExtractor() {
        const uid = this.router.snapshot.paramMap.get('uid');
        this.extractorService.find(uid).subscribe((extractorItem) => {
            this.controlvar.extractor = extractorItem.extractor;
            this.cvFields = this.getFieldsCVToInclude().slice();
            this.extractorFields = extractorItem.fields;
            this.extractorDisableFields = this.extractorFields.filter((x) => (this.controlvar.extractor[x.name])).slice();
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

    private getFieldsCVToInclude() {
        return Object.keys(this.controlvar).filter((x) => this.getFieldsCVToExclude().indexOf(x) === -1);
    }

    private getFieldsExtractorToInclude() {
        return Object.keys(this.controlvar.extractor).filter((x) => this.getFieldsExtractorToExclude().indexOf(x) === -1);
    }

    save() {
        this.isSaving = true;
        const newCV: NewCV = new NewCV();
        newCV.extractorClass = this.controlvar.extractor.extractorClass;
        newCV.controlVariable = this.controlvar;
        this.extractorFields
            .forEach((x) => (newCV.extractorData[x.name] = this.controlvar.extractor[x.name]));
        this.subscribeToSaveResponse(
            this.controlvarService.create(newCV));
    }

    private subscribeToSaveResponse(result: Observable<NewCV>) {
        result.subscribe(
            (res: NewCV) => (this.onSaveSuccess(res)),
            (res: Response) => (this.onSaveError(res)));
    }

    private onSaveSuccess(result: NewCV) {
        this.eventManager.broadcast({ name: 'acmeListModification', content: 'OK'});
        this.isSaving = false;
        this.mainRouter.navigateByUrl('/cv');
    }

    private onSaveError(error) {
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

    cancel() {
    }

    requiredField(field: DeccomField) {
        return this.controlvar.extractor[field.name] ? '' : null
    }

    disableField(field: DeccomField) {
        return this.extractorDisableFields.indexOf(field) !== -1 ? '' : null;
    }

    onClickCustomField(field: DeccomField) {
        if (field.component === '') {
            return;
        }
        if (this.currentField === field) {
            this.currentField = null;
            return;
        }
        this.currentField = field;
    }
}
