
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EventComponent } from './event.component';

export const eventRoute: Routes = [
    {
        path: 'event',
        component: EventComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.event.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
