import { Component, HostListener, Input, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CVCard } from './cv.model';
import { CVService } from './cv.service';
import { ResponseWrapper, ITEMS_PER_PAGE } from '../../shared';
import { JhiParseLinks, JhiAlertService, JhiEventManager } from 'ng-jhipster';

@Component({
    selector: 'jhi-cv',
    templateUrl: './cv.component.html',
    styleUrls: ['./cv.component.css']
})
export class CVComponent implements OnInit, OnDestroy {

    cvCards: CVCard[];
    page: any;
    itemsPerPage: number;
    links: any;

    constructor(
        public cvService: CVService,
        private parseLinks: JhiParseLinks,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
    ) {
        this.cvCards = [];
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
        this.cvService.findAll(pageSettings).subscribe(
            (data: any) => this.onSuccess(data.json(), data.headers),
            (error: Response) => this.onError(error)
        )
    }

    ngOnDestroy() { }

    onSuccess(data: any, headers: any) {
        for (let i = 0; i < data.content.length; i++) {
            const cvCard: CVCard = {
                name: data.content[i]['name'],
                creationMoment: data.content[i]['creationMoment'],
                status: data.content[i]['status'],
                controlVarEntries: data.content[i]['controlVarEntries'],
            };
            this.cvCards.push(cvCard);
        }
        // this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'all_success', content: 'OK' });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    getRunningCards() {
        return this.filterCardsByStatus('RUNNING');
    }

    getPausedCards() {
        return this.filterCardsByStatus('PAUSED');
    }

    getBlockedCards() {
        return this.filterCardsByStatus('BLOCKED');
    }

    filterCardsByStatus(status: String) {
        return this.cvCards.filter((cvCard) => cvCard.status === status);
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
