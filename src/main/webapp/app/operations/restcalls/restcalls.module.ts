import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';

import { DeccomSharedModule } from '../../shared';
import { DeccomJSONVisualizerModule } from './jsonvisualizer';

import {
    RestCallsService,
    RestCallsComponent,
    RestCallsShowComponent,
    restcallsRoute
} from './';

const ENTITY_STATES = [
    ...restcallsRoute,
];

@NgModule({
    imports: [
        DeccomSharedModule,
        DeccomJSONVisualizerModule,
        InfiniteScrollModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RestCallsComponent,
        RestCallsShowComponent
    ],
    entryComponents: [
        RestCallsComponent
    ],
    providers: [
        RestCallsService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomRestCallsModule { }
