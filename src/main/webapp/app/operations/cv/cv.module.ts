import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';

import { DeccomSharedModule } from '../../shared';

import {
    CVService,
    CVComponent,
    CVCardComponent,
    cvRoute,
    cvPopupRoute,
    CVPopupService,
    CVDialogComponent,
    CVPopupComponent,
    CVDeletePopupComponent,
    CVDeleteDialogComponent,
} from './';

const ENTITY_STATES = [
    ...cvRoute,
    ...cvPopupRoute,
];

@NgModule({
    imports: [
        DeccomSharedModule,
        InfiniteScrollModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CVComponent,
        CVCardComponent,
        CVDialogComponent,
        CVPopupComponent,
        CVDeletePopupComponent,
        CVDeleteDialogComponent
    ],
    entryComponents: [
        CVComponent,
        CVCardComponent,
        CVDialogComponent,
        CVPopupComponent,
        CVDeletePopupComponent,
        CVDeleteDialogComponent
    ],
    providers: [
        CVService,
        CVPopupService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomCVModule { }
