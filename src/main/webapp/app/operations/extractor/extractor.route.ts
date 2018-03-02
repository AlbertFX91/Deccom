import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ExtractorListComponent } from './extractor-list.component';
import { ExtractorCreationComponent } from './extractor-creation.component';

export const extractorRoute: Routes = [
    {
        path: 'extractors',
        component: ExtractorListComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.extractor.home.title',
            animation: {
                  value: 'extractors',
            }
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'extractors/:uid',
        component: ExtractorCreationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'deccomApp.extractor.home.title',
            animation: {
                value: 'extractors-create',
          }
        },
        canActivate: [UserRouteAccessService]
    }
];
