import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';

import { DeccomSharedModule } from '../../shared';
import { DeccomJSONVisualizerModule } from './jsonvisualizer';

import {
    RESTService,
    RESTComponent,
    restRoute
} from './';

const ENTITY_STATES = [
    ...restRoute,
];

@NgModule({
    imports: [
        DeccomSharedModule,
        DeccomJSONVisualizerModule,
        InfiniteScrollModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RESTComponent
    ],
    entryComponents: [
        RESTComponent
    ],
    providers: [
        RESTService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomRESTModule { }
