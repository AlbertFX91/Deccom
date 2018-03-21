import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeccomSharedModule } from '../../shared';
import { DeccomFieldsModule } from './fields'
import {
    ExtractorListComponent,
    ExtractorCreationComponent,
    ExtractorService,
    extractorRoute,
    ExtractorDirective,
} from './';

const ENTITY_STATES = [
    ...extractorRoute,
];

@NgModule({
    imports: [
        DeccomSharedModule,
        DeccomFieldsModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ExtractorListComponent,
        ExtractorCreationComponent,
        ExtractorDirective,
    ],
    entryComponents: [
        ExtractorListComponent,
    ],
    providers: [
        ExtractorService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomExtractorModule {}
