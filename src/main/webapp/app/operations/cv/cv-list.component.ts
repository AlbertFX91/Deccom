import { Component, HostListener, Input, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CVCard } from './cv.model';
import { CVService } from './cv.service';

@Component({
    selector: 'jhi-cv-list',
    templateUrl: './cv-list.component.html',
    styleUrls: ['./cv-list.component.css']
})
export class CVListComponent implements OnInit, OnDestroy {

    @Input()
    cvCards: CVCard[];

    constructor(
        public cvService: CVService
    ) { }

    ngOnInit() { }

    ngOnDestroy() { }

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
