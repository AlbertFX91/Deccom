import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FacebookComponent } from './facebook.component';

export const facebookRoute: Routes = [
    {
        path: 'facebook',
        component: FacebookComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.Facebook.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
