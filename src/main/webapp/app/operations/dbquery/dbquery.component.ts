import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Acme } from '../../entities/acme/acme.model';
import { AcmeService } from '../../entities/acme/acme.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-dbquery',
    templateUrl: './dbquery.component.html'
})
export class DBQueryComponent implements OnInit, OnDestroy {

    constructor(
        private acmeService: AcmeService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {}

    ngOnInit() {}

    ngOnDestroy() {}

}
