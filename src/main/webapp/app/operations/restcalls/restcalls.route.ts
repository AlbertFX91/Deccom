
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RestCallsComponent } from './restcalls.component';

export const restcallsRoute: Routes = [
    {
        path: 'restcalls',
        component: RestCallsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.apiRESTCalls.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
