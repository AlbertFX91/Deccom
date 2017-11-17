import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TwitterComponent } from './twitter.component';

export const twitterRoute: Routes = [
    {
        path: 'twitter',
        component: TwitterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.Twitter.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
