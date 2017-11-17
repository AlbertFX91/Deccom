import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CVCard } from './cv.model';
import { CVService } from './cv.service';
import { trigger, state, style, transition, animate, keyframes } from '@angular/animations';

@Component({
    selector: 'jhi-cv-card',
    templateUrl: './cv-card.component.html',
    styleUrls: ['./cv-card.component.css'],
    animations: [
        trigger('collapseAnimation', [
            state('isCollapsed', style({
                transform: 'scale(1)'
            })),
            state('!isCollapsed', style({
                transform: 'scale(1.2)'
            })),
            transition('isCollapsed <=> !isCollapsed', animate('300ms ease-in', keyframes([
                style({opacity: 0, offset: 0}),
                style({opacity: 1, offset: 1})
            ])))
        ])
    ]
})
export class CVCardComponent implements OnInit, OnDestroy {

    @Input()
    cvCard: CVCard;

    public isCollapsed;

    constructor(
        private cvService: CVService
    ) {
        this.isCollapsed = true;
    }

    ngOnInit() { }

    ngOnDestroy() { }

}
