import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../../shared';
import { PaginationConfig } from '../../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-jsonvisualizer',
    templateUrl: './jsonvisualizer.component.html',
    styleUrls: [
        'jsonvisualizer.css'
    ]
}) export class JSONVisualizerComponent implements OnInit, OnDestroy {

    @Input()
    data: any;

    constructor() { }

    ngOnInit() {
        // Array test
        this.data['dataExample'] = ['data1', 'data2', 'data3'];
    }

    ngOnDestroy() { }

    keys() {
        return Object.keys(this.data);
    }

    typeByKey(key: string) {
        // Value of key
        const value = this.data[key];
        // Type of value
        const type = typeof (value);
        // Get type if value is not an array, dict or null (string, number or boolean)
        if (type !== 'object' && type !== 'undefined') {
            return type;
        }
        // Get type of value when value is array
        if (Array.isArray(value)) {
            return 'array';
        }
        // Get type of value when value is an object and not null or array (JSON)
        if (type === 'object' && value !== null) {
            return 'json';
        }
    }

}
