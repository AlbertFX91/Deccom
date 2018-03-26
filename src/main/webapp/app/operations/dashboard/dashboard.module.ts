import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { ChartsModule } from 'ng2-charts';

import { DeccomSharedModule } from '../../shared';

import {
    DashboardComponent,
    dashboardRoute
} from './';

const ENTITY_STATES = [
    ...dashboardRoute
];

@NgModule({
    imports: [
        DeccomSharedModule,
        InfiniteScrollModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        ChartsModule
    ],
    declarations: [
        DashboardComponent
    ],
    entryComponents: [
        DashboardComponent
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomDashboardModule { }
