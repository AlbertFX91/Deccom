import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';

import { DeccomSharedModule } from '../../shared';
import { DeccomJSONVisualizerModule } from '../jsonvisualizer';

import {
    EventService,
    EventComponent,
    eventRoute
} from './';

const ENTITY_STATES = [
    ...eventRoute,
];

@NgModule({
    imports: [
        DeccomSharedModule,
        DeccomJSONVisualizerModule,
        InfiniteScrollModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EventComponent
    ],
    entryComponents: [
        EventComponent
    ],
    providers: [
        EventService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomEventModule { }
