import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeccomSharedModule } from '../../../shared';

import { FieldService } from './fields.service';

import { components } from './';
@NgModule({
    imports: [
       DeccomSharedModule,
    ],
    declarations: components(),
    entryComponents: components(),
    providers: [
        FieldService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomFieldsModule {}
