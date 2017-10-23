import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CVService } from './cv.service';
import { CVCard } from './cv.model';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-cv-dashboard',
    templateUrl: './cv-dashboard.component.html',
    styleUrls: ['./cv-dashboard.component.css']
}) export class CVDashboardComponent implements OnInit, OnDestroy {

    /*
    runningCards: CVCard[];
    pausedCards: CVCard[];
    blockedCards: CVCard[];
    */

    constructor(
        public cvService: CVService
    ) {
        /*
        this.runningCards = cvService.getRunningCards();
        this.pausedCards = cvService.getPausedCards();
        this.blockedCards = cvService.getBlockedCards();
        */
    }

    ngOnInit() { }

    ngOnDestroy() { }

}
