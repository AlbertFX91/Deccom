import { Component, HostListener, Input, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CVCard } from './cv.model';
import { CVService } from './cv.service';
import { ResponseWrapper } from '../../shared';
import { JhiParseLinks, JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-cv',
    templateUrl: './cv.component.html',
    styleUrls: ['./cv.component.css']
})
export class CVComponent implements OnInit, OnDestroy {

    cvCards: CVCard[];
    links: any;
    totalItems: number;

    constructor(
        public cvService: CVService,
        private parseLinks: JhiParseLinks,
        private alertService: JhiAlertService
    ) {
        this.cvCards = [];
        this.links = {
            last: 0
        };
    }

    ngOnInit() { }

    ngOnDestroy() { }

    loadAll() {
        this.cvService.query({}).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.cvCards.push(data[i]);
        }
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    allowDrop($event) {
        $event.preventDefault();
    }

    drop($event) {
        $event.preventDefault();
        const data = $event.dataTransfer.getData('name');

        let target = $event.target;
        const targetClassName = target.className;

        while (target.className !== 'list') {
            target = target.parentNode;
        }
        target = target.querySelector('.cards');

        if (targetClassName === 'card') {
            $event.target.parentNode.insertBefore(document.getElementById(data), $event.target);
        } else if (targetClassName === 'list__title') {
            if (target.children.length) {
                target.insertBefore(document.getElementById(data), target.children[0]);
            } else {
                target.appendChild(document.getElementById(data));
            }
        } else {
            target.appendChild(document.getElementById(data));
        }

    }

}
