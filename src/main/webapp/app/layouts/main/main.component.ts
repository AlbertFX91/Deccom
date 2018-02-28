import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd, RoutesRecognized } from '@angular/router';

import { JhiLanguageHelper, StateStorageService, LoginService } from '../../shared';

import {trigger, animate, style, group, animateChild, query, stagger, transition} from '@angular/animations';

const slideLeft = [
    query(':leave', style({ position: 'absolute', left: 0, right: 0, transform: 'translate3d(0%,0,0)' }), {optional: true}),
    query(':enter', style({ position: 'absolute', left: 0, right: 0, transform: 'translate3d(-100%,0,0)' }), {optional: true}),
    group([
      query(':leave', group([
        animate('500ms cubic-bezier(.35,0,.25,1)', style({ transform: 'translate3d(100%,0,0)' })), // y: '-100%'
      ]), {optional: true}),
      query(':enter', group([
        animate('500ms cubic-bezier(.35,0,.25,1)', style({ transform: 'translate3d(0%,0,0)' })),
      ]), {optional: true})
    ])
]

const slideRight = [
    query(':leave', style({ position: 'absolute', left: 0, right: 0, transform: 'translate3d(0%,0,0)'}), {optional: true}),
    query(':enter', style({ position: 'absolute', left: 0, right: 0, transform: 'translate3d(100%,0,0)'}), {optional: true}),

    group([
      query(':leave', group([
        animate('500ms cubic-bezier(.35,0,.25,1)', style({ transform: 'translate3d(-100%,0,0)' })), // y: '-100%'
      ]), {optional: true}),
      query(':enter', group([
        animate('500ms cubic-bezier(.35,0,.25,1)', style({ transform: 'translate3d(0%,0,0)' })),
      ]), {optional: true})
    ])
]

@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html',
    styleUrls: [
        'main.css'
    ],
    animations: [
        trigger('routerAnimations', [
          transition('extractors => extractors-create', slideRight),
          transition('extractors-create => extractors', slideLeft),
        ])
      ]
})
export class JhiMainComponent implements OnInit {

    displaySidebar: boolean;

    constructor(
        private jhiLanguageHelper: JhiLanguageHelper,
        private router: Router,
        private $storageService: StateStorageService,
        private loginService: LoginService,
    ) {}

    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = (routeSnapshot.data && routeSnapshot.data['pageTitle']) ? routeSnapshot.data['pageTitle'] : 'deccomApp';
        if (routeSnapshot.firstChild) {
            title = this.getPageTitle(routeSnapshot.firstChild) || title;
        }
        return title;
    }

    ngOnInit() {
        this.router.events.subscribe((event) => {
            if (event instanceof NavigationEnd) {
                this.jhiLanguageHelper.updateTitle(this.getPageTitle(this.router.routerState.snapshot.root));
            }
        });
        this.displaySidebar = false;
    }

    onDisplaySidebar(displaySidebar: boolean) {
        this.displaySidebar = displaySidebar;
    }

    prepareRouteTransition(outlet) {
        const animation = outlet.activatedRouteData['animation'] || {};
        return animation['value'] || null;
    }
}
