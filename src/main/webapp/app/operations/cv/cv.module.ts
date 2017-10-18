import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';

import { DeccomSharedModule } from '../../shared';

import {
    CVService,
    CVDashboardComponent,
    restRoute
} from './';

const ENTITY_STATES = [
    ...restRoute,
];

@NgModule({
    imports: [
        DeccomSharedModule,
        InfiniteScrollModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CVDashboardComponent
    ],
    entryComponents: [
        CVDashboardComponent
    ],
    providers: [
        CVService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomRESTModule { }
