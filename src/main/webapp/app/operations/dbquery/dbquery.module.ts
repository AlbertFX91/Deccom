import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeccomSharedModule } from '../../shared';
import {
    DBQueryService,
    DBQueryComponent,
    DBQueryResultComponent,
    dbqueryRoute,
} from './';

const ENTITY_STATES = [
    ...dbqueryRoute,
];

@NgModule({
    imports: [
        DeccomSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DBQueryComponent,
        DBQueryResultComponent,
    ],
    entryComponents: [
        DBQueryComponent,
    ],
    providers: [
        DBQueryService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomDBQueryModule {}
