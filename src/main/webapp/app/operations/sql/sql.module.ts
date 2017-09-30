import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeccomSharedModule } from '../../shared';
import {
    SQLService,
    SQLComponent,
    SQLResultComponent,
    sqlRoute,
} from './';

const ENTITY_STATES = [
    ...sqlRoute,
];

@NgModule({
    imports: [
        DeccomSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SQLComponent,
        SQLResultComponent,
    ],
    entryComponents: [
        SQLComponent,
    ],
    providers: [
        SQLService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomSQLModule {}
