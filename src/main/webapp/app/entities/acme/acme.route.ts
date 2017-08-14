import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AcmeComponent } from './acme.component';
import { AcmeDetailComponent } from './acme-detail.component';
import { AcmePopupComponent } from './acme-dialog.component';
import { AcmeDeletePopupComponent } from './acme-delete-dialog.component';

export const acmeRoute: Routes = [
    {
        path: 'acme',
        component: AcmeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.acme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'acme/:id',
        component: AcmeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.acme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const acmePopupRoute: Routes = [
    {
        path: 'acme-new',
        component: AcmePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.acme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'acme/:id/edit',
        component: AcmePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.acme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'acme/:id/delete',
        component: AcmeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.acme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
