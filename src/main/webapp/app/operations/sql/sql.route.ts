import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SQLComponent } from './sql.component';

export const sqlRoute: Routes = [
    {
        path: 'sql',
        component: SQLComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.sql.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
