import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeccomSharedModule } from '../../../../shared';

import { FieldSQLQueryComponent} from './';
@NgModule({
    imports: [
       DeccomSharedModule,
    ],
    declarations: [FieldSQLQueryComponent],
    entryComponents: [FieldSQLQueryComponent],
    providers: [
    ],
    exports: [FieldSQLQueryComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomFieldsSQLQueryModule {}
