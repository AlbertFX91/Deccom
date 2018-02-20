import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ExtractorListComponent } from './extractor-list.component';

export const extractorRoute: Routes = [
    {
        path: 'extractor',
        component: ExtractorListComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.extractor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
