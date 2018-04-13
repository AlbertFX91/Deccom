import { Component, OnInit, OnDestroy, ViewChild, ComponentFactoryResolver, Input, OnChanges, SimpleChange, SimpleChanges, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../../shared';
import { PaginationConfig } from '../../../blocks/config/uib-pagination.config';
import { FieldDirective } from './fields.directive'
import { FieldService } from './fields.service'
import { DeccomField } from '../extractor.model'
import { CV } from '../../cv/cv.model';

@Component({
    selector: 'jhi-fields',
    templateUrl: './fields.component.html',
    styleUrls: ['./fields.component.css']
})
export class FieldComponent implements OnInit, OnDestroy, OnChanges {
    @Input() field: DeccomField;
    @Input() cv: CV;
    @Output()
    private finished = new EventEmitter<boolean>();

    private _init: boolean;

    currentAccount: any;

    @ViewChild(FieldDirective) jhiField: FieldDirective;
    constructor(
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private router: ActivatedRoute,
        private mainRouter: Router,
        private componentFactoryResolver: ComponentFactoryResolver,
        private fieldService: FieldService,
    ) {
    }

    ngOnInit() {
        this._init = true;
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
    }

    ngOnDestroy() { }

    ngOnChanges(changes: SimpleChanges) {
        if (this._init && changes.field) {
            this._init = false;
            this.loadCustomField(this.field.component);
        }
    }
    private loadCustomField(comp: string) {

        const componentFactory = this.componentFactoryResolver.resolveComponentFactory(this.fieldService.get(comp));

        const viewContainerRef = this.jhiField.viewContainerRef;
        viewContainerRef.clear();

        const componentRef = viewContainerRef.createComponent(componentFactory);

        const inst = <FieldBaseComponent>componentRef.instance;
        inst['field'] = this.field;
        inst['cv'] = this.cv;
        inst['finished'].subscribe((res) => this.finished.emit(res) );
    }
}

@Component({
    selector: 'jhi-fields-base'
})
export class FieldBaseComponent implements OnInit, OnDestroy {
    @Input() field: DeccomField;
    @Input() cv: CV;
    @Output()
    private finished = new EventEmitter<boolean>();

    ngOnInit() {
    }

    ngOnDestroy() { }

    getValue(attr: string) {
        return this.cv.extractor[attr];
    }

    setValue(val: any) {
        this.cv.extractor[this.field.name] = val;
    }

    setValueByAttr(attr: string, val: any) {
        this.cv.extractor[attr] = val;
    }

    finish() {
        this.finished.emit(true);
    }
}
