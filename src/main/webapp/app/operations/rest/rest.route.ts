
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RESTComponent } from './rest.component';

export const restRoute: Routes = [
    {
        path: 'rest',
        component: RESTComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.REST.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
