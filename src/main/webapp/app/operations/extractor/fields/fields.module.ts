import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeccomSharedModule } from '../../../shared';

import { FieldService } from './fields.service';

import { componentsList, modulesList, FieldComponent, FieldBaseComponent, FieldDirective } from './';

import { DeccomFieldsJsonPathModule } from './jsonpath/jsonpath.module'
import { DeccomFieldsSQLQueryModule } from './query/query.module'

@NgModule({
    imports: [
       DeccomSharedModule,
       modulesList
    ],
    declarations: [FieldComponent, FieldBaseComponent, FieldDirective],
    entryComponents: [FieldComponent, componentsList],
    providers: [
        FieldService,
    ],
    exports: [FieldComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomFieldsModule {}
