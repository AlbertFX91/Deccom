import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { CVCard } from './cv.model';
import { CVService } from './cv.service';

@Component({
    selector: 'jhi-cv-card',
    templateUrl: './cv-card.component.html',
    styleUrls: ['./cv-card.component.css']
})
export class CVCardComponent implements OnInit, OnDestroy {

    @Input()
    cvCard: CVCard;

    constructor( ) { }

    ngOnInit() { }

    ngOnDestroy() { }

    dragStart(ev) {
        ev.dataTransfer.setData('text', ev.target.id);
    }

}
