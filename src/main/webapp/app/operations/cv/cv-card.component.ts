import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CV } from './cv.model';
import { CVService } from './cv.service';
import { trigger, state, style, transition, animate, keyframes } from '@angular/animations';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';

import * as $ from 'jquery';

@Component({
    selector: 'jhi-cv-card',
    templateUrl: './cv-card.component.html',
    styleUrls: ['./cv-card.component.css'],
    providers: [NgbCarouselConfig]
})
export class CVCardComponent implements OnInit, OnDestroy {

    @Input()
    cvCard: CV;

    public isCollapsed;

    constructor(
        private cvService: CVService,
        config: NgbCarouselConfig
    ) {
        this.isCollapsed = true;
        config.interval = 0;
        config.wrap = false;
        config.keyboard = false;
    }

    ngOnInit() { 
    }

    ngOnDestroy() { }

    getClassByStatus() {
        const data = {
            'RUNNING': 'green',
            'PAUSED': 'yellow',
            'BLOCKED': 'red'
        };
        return data[this.cvCard.status];

    }

    toggle() {
        this.isCollapsed = !this.isCollapsed;
    }
}
