import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';

import { DeccomSharedModule } from '../../shared';

import {
    CVService,
    // CVDashboardComponent,
    CVListComponent,
    cvRoute
} from './';

const ENTITY_STATES = [
    ...cvRoute,
];

@NgModule({
    imports: [
        DeccomSharedModule,
        InfiniteScrollModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        // CVDashboardComponent
        CVListComponent
    ],
    entryComponents: [
        // CVDashboardComponent
        CVListComponent
    ],
    providers: [
        CVService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomCVModule { }
