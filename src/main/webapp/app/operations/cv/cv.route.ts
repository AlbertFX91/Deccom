import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CVDashboardComponent } from './cv-dashboard.component';

export const cvRoute: Routes = [
    {
        path: 'cv',
        component: CVDashboardComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.ControlVar.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
