import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CVService } from './cv.service';
import { CVCard, CVCardList } from './cv.model';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-cv-dashboard',
    templateUrl: './cv-dashboard.component.html',
    styleUrls: ['./cv-dashboard.component.css']
}) export class CVDashboardComponent implements OnInit, OnDestroy {

    constructor(
        private cvService: CVService,
    ) { }

    ngOnInit() { }

    ngOnDestroy() { }

}
