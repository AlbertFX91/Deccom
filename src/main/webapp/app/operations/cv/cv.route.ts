import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CVComponent } from './cv.component';

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
