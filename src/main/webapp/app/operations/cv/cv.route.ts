import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CVComponent } from './cv.component';
import { CVPopupComponent } from './cv-dialog.component';
import { CVDeletePopupComponent } from './cv-delete-dialog.component';

export const cvRoute: Routes = [
    {
        path: 'cv',
        component: CVComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.ControlVar.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cvPopupRoute: Routes = [
    {
        path: 'cv/:id/edit',
        component: CVPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.ControlVar.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cv/:id/delete',
        component: CVDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.ControlVar.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
