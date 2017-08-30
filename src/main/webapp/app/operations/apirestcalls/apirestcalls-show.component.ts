import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { APIRestCallsService } from './apirestcalls.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-apirestcalls-show',
    templateUrl: './apirestcalls-show.component.html'
})
export class APIRestCallsShowComponent implements OnInit, OnDestroy {

    @Input()
    response: any;

    constructor(
        private apirestcallsService: APIRestCallsService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) { }

    ngOnInit() { }

    ngOnDestroy() { }

    listOrDetail() {
        if (this.response.length > 1) {
            return true;
        } else {
            return false;
        }
    }

    onSuccess() {
        return Object.keys(this.response[0]);
    }

}
