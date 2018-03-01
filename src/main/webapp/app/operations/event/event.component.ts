import { Component, HostListener, Input, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Event } from './event.model';
import { EventService } from './event.service';
import { ResponseWrapper, ITEMS_PER_PAGE } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import { JhiParseLinks, JhiAlertService, JhiEventManager } from 'ng-jhipster';

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
    event: Event;
    isSaving: boolean;

    constructor(
        public eventService: EventService,
        private parseLinks: JhiParseLinks,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
    ) {
        this.events = [];
        this.page = 0;
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.links = {
            last: 0
        };
    }

    ngOnInit() {
        const pageSettings = {
            page: this.page,
            size: this.itemsPerPage
        };
        this.eventService.findAll(pageSettings).subscribe(
            (data: any) => this.onSuccess(data.json(), data.headers),
            (error: Response) => this.onError(error)
        )
        this.isSaving = false;
        this.event = {};
    }

    ngOnDestroy() { }

    onSuccess(data: any, headers: any) {
        for (let i = 0; i < data.length; i++) {
            const event: Event = {
                id: data[i]['id'],
                name: data[i]['name'],
                creationMoment: data[i]['creationMoment'],
                startingDate: data[i]['startingDate'],
                endingDate: data[i]['endingDate']
            };
            this.events.push(event);
        }
        // this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'all_success', content: 'OK' });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    createEvent() {
        this.isSaving = true;
        this.eventService.create(this.event).subscribe(
            (res: any) => this.onEventSuccess(res),
            (error: Response) => this.onError(error)
        )
    }

    onEventSuccess(res: any) {
        this.isSaving = false;
        // this.eventManager.broadcast({ name: 'event_success', content: 'OK' });
        this.eventManager.broadcast({ name: 'eventListModification', content: 'OK' });
        this.clear();
    }

    delete(id: string) {
        this.eventService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'eventListModification',
                content: 'Deleted an event'
            });
        });
    }

    clear() {
        this.event = {};
    }

    loadAll() {
        this.eventService.query({
            page: this.page,
            size: this.itemsPerPage
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

    reset() {
        this.page = 0;
        this.events = [];
        this.loadAll();
    }

}
