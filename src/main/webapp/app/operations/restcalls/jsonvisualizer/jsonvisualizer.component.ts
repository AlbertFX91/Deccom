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

    key_style = {
        'number': ['fa', 'fa-circle', 'number'],
        'string': ['fa', 'fa-circle', 'string'],
        'boolean': ['fa', 'fa-circle', 'boolean'],
        'array': ['brackets', 'img-fluid'],
        'json': ['keys', 'img-fluid']
    };

    constructor() { }

    ngOnInit() {
        // Array test
        /// this.data['arrayExample'] = ['data1', 'data2', 'data3'];
        // Boolean test
        this.data['booleanExample'] = true;
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

    styleByKey(key: string) {
        return this.key_style[this.typeByKey(key)];
    }

    showValue(key: string) {
        const type = this.typeByKey(key);
        return type !== 'array' && type !== 'json';
    }

    valueByKey(key: string) {
        const value = this.data[key];
        if (this.typeByKey(key) === 'string') {
            return '"' + value + '"';
        }
        return value;
    }
}
