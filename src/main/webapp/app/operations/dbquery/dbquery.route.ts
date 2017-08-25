import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DBQueryComponent } from './dbquery.component';

export const dbqueryRoute: Routes = [
    {
        path: 'dbquery',
        component: DBQueryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.dbquery.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
