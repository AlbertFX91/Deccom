import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DeccomSharedModule } from '../../../../../shared';

import {
    JSONVisualizerComponent,
    JSONVisualizerEntryComponent
} from './';

@NgModule({
    imports: [
        DeccomSharedModule
    ],
    declarations: [
        JSONVisualizerComponent,
        JSONVisualizerEntryComponent
    ],
    entryComponents: [
        JSONVisualizerComponent
    ],
    exports: [
        JSONVisualizerComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomJSONVisualizerModule { }
