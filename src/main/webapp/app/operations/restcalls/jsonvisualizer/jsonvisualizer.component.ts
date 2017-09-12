import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../../shared';
import { PaginationConfig } from '../../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-jsonvisualizer',
    templateUrl: './jsonvisualizer.component.html'
}) export class JSONVisualizerComponent implements OnInit, OnDestroy {

    @Input()
    data: any;

    constructor() { }

    ngOnInit() { }

    ngOnDestroy() { }

}
