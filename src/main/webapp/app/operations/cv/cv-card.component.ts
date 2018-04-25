import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CV } from './cv.model';
import { CVService } from './cv.service';
import { trigger, state, style, transition, animate, keyframes } from '@angular/animations';

@Component({
    selector: 'jhi-cv-card',
    templateUrl: './cv-card.component.html',
    styleUrls: ['./cv-card.component.css']
})
export class CVCardComponent implements OnInit, OnDestroy {

    @Input()
    cvCard: CV;

    public isCollapsed;

    constructor(
        private cvService: CVService
    ) {
        this.isCollapsed = true;
    }

    ngOnInit() { }

    ngOnDestroy() { }

    getClassByStatus() {
        const data = {
            'RUNNING': 'green',
            'PAUSED': 'yellow',
            'BLOCKED': 'red'
        };
        return data[this.cvCard.status];

    }
}
