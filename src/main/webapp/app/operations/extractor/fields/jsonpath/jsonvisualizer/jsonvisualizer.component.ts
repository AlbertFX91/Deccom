import { Component, OnInit, OnDestroy, Input, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../../../../shared';
import { PaginationConfig } from '../../../../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-jsonvisualizer',
    templateUrl: './jsonvisualizer.component.html'
}) export class JSONVisualizerComponent implements OnInit, OnDestroy {

    @Input()
    data: any;

    @Output()
    private path = new EventEmitter<String>();

    @Output()
    private correctPath = new EventEmitter<boolean>();

    constructor() { }

    ngOnInit() { }

    ngOnDestroy() { }

    constructPath(path: string) {
        this.path.emit('$.' + path);
    }

    type(data: any) {
        // Type of value
        const type = typeof (data);
        // Get type if value is not an array, dict or null (string, number or boolean)
        if (type !== 'object' && type !== 'undefined') {
            return type;
        }
        // Get type of value when value is array
        if (Array.isArray(data)) {
            return 'array';
        }
        // Get type of value when value is an object and not null or array (JSON)
        if (type === 'object' && data !== null) {
            return 'json';
        }
    }
    onCorrectPath(e) {
        this.correctPath.emit(e);
    }
}
