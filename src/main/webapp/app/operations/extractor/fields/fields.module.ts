import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeccomSharedModule } from '../../../shared';

import { FieldService } from './fields.service';

import { components, modules, FieldComponent, FieldDirective } from './';

import { DeccomFieldsJsonPathModule } from './jsonpath/jsonpath.module'
import { DeccomFieldsSQLQueryModule } from './query/query.module'

@NgModule({
    imports: [
       DeccomSharedModule,
       modules(),
    ],
    declarations: [FieldComponent, FieldDirective],
    entryComponents: [components(), FieldComponent],
    providers: [
        FieldService,
    ],
    exports: [FieldComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomFieldsModule {}
