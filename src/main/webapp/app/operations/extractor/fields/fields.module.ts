import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeccomSharedModule } from '../../../shared';

import { FieldService } from './fields.service';

import { components, FieldComponent, FieldDirective } from './';
@NgModule({
    imports: [
       DeccomSharedModule,
    ],
    declarations: [components(), FieldComponent, FieldDirective],
    entryComponents: [components(), FieldComponent],
    providers: [
        FieldService,
    ],
    exports: [FieldComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomFieldsModule {}
