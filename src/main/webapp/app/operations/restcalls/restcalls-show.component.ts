import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { RestCallsService } from './restcalls.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-restcalls-show',
    templateUrl: './restcalls-show.component.html'
})
export class RestCallsShowComponent implements OnInit, OnDestroy {

    @Input()
    data: any;

    constructor(
        private apirestcallsService: RestCallsService,
        private eventManager: JhiEventManager
    ) { }

    ngOnInit() { }

    ngOnDestroy() { }

    onSuccess() {
        return Object.keys(this.data[0]).sort();
    }

}
