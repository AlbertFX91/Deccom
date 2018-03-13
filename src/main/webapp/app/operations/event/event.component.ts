import { Component, HostListener, Input, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Event } from './event.model';
import { EventService } from './event.service';
import { ResponseWrapper, ITEMS_PER_PAGE } from '../../shared';

@Component({
    selector: 'jhi-event',
    templateUrl: './event.component.html'
}) export class EventComponent implements OnInit, OnDestroy {

    events: Event[];
    page: any;
    itemsPerPage: number;
    links: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    eventSubscriber: Subscription;
    predicate: any;

    constructor(
        public eventService: EventService,
        private parseLinks: JhiParseLinks,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
        this.events = [];
        this.page = 0;
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    ngOnInit() {
        const pageSettings = {
            page: this.page,
            size: this.itemsPerPage
        };
        this.loadAll();
        this.registerChangeInEvents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    onSuccess(data: any, headers: any) {
        for (let i = 0; i < data.length; i++) {
            this.events.push(data[i]);
        }
        // this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'all_success', content: 'OK' });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    loadAll() {
        this.eventService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    registerChangeInEvents() {
        this.eventSubscriber = this.eventManager.subscribe('eventListModification', (response) => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    reset() {
        this.page = 0;
        this.events = [];
        this.loadAll();
    }

    reload() {
        this.events = [];
        this.ngOnInit();
    }

}
