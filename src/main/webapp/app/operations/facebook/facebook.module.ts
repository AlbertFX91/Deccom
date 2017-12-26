import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';

import { DeccomSharedModule } from '../../shared';
import { DeccomJSONVisualizerModule } from '../jsonvisualizer';

import {
    FacebookComponent,
    facebookRoute
} from './';

const ENTITY_STATES = [
    ...facebookRoute,
];

@NgModule({
    imports: [
        DeccomSharedModule,
        DeccomJSONVisualizerModule,
        InfiniteScrollModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FacebookComponent
    ],
    entryComponents: [
        FacebookComponent
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomFacebookModule { }
