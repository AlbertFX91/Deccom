import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { ChartsModule } from 'ng2-charts';

import { DeccomSharedModule } from '../../shared';

import {
    DashboardComponent,
    DashboardMinifiedComponent,
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
        DashboardComponent,
        DashboardMinifiedComponent
    ],
    entryComponents: [
        DashboardComponent
    ],
    exports: [
        DashboardMinifiedComponent
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomDashboardModule { }
