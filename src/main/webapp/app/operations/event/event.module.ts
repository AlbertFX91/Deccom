import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';

import { DeccomSharedModule } from '../../shared';

import {
    EventService,
    EventPopupService,
    EventComponent,
    EventDialogComponent,
    EventPopupComponent,
    eventRoute,
    eventPopupRoute
} from './';

const ENTITY_STATES = [
    ...eventRoute,
    ...eventPopupRoute
];

@NgModule({
    imports: [
        DeccomSharedModule,
        InfiniteScrollModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EventComponent,
        EventDialogComponent,
        EventPopupComponent
    ],
    entryComponents: [
        EventComponent,
        EventDialogComponent,
        EventPopupComponent
    ],
    providers: [
        EventService,
        EventPopupService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomEventModule { }
