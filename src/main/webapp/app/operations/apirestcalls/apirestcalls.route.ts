
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { APIRestCallsComponent } from './apirestcalls.component';

export const apirestcallsRoute: Routes = [
    {
        path: 'apirestcalls',
        component: APIRestCallsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.apirestcalls.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
