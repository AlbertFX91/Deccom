import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeccomSharedModule } from '../../shared';
import {
    ExtractorListComponent,
    ExtractorCreationComponent,
    ExtractorService,
    extractorRoute,
} from './';

const ENTITY_STATES = [
    ...extractorRoute,
];

@NgModule({
    imports: [
        DeccomSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ExtractorListComponent,
        ExtractorCreationComponent,
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
