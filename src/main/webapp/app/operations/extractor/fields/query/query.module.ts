import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeccomSharedModule } from '../../../../shared';

import {
    FieldSQLQueryComponent,
    FieldSQLQueryResultComponent,
    SQLQueryService,
} from './';
@NgModule({
    imports: [
        DeccomSharedModule,
    ],
    declarations: [FieldSQLQueryComponent, FieldSQLQueryResultComponent],
    entryComponents: [FieldSQLQueryComponent],
    exports: [FieldSQLQueryComponent],
    providers: [ SQLQueryService ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomFieldsSQLQueryModule {}
