import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';

import { DeccomSharedModule } from '../../shared';
import { DeccomJSONVisualizerModule } from '../jsonvisualizer';
import { TwitterService } from './twitter.service';

import {
    TwitterComponent,
    twitterRoute
} from './';

const ENTITY_STATES = [
    ...twitterRoute,
];

@NgModule({
    imports: [
        DeccomSharedModule,
        DeccomJSONVisualizerModule,
        InfiniteScrollModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TwitterComponent
    ],
    entryComponents: [
        TwitterComponent
    ],
    providers: [
        TwitterService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomTwitterModule { }
