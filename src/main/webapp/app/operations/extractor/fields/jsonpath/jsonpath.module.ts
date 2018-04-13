import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeccomSharedModule } from '../../../../shared';

import { FieldRESTJsonPathComponent} from './';
import { JSONPathService } from './jsonpath.service';
import { DeccomJSONVisualizerModule } from './jsonvisualizer/';

@NgModule({
    imports: [
        DeccomSharedModule,
        DeccomJSONVisualizerModule,
    ],
    declarations: [FieldRESTJsonPathComponent],
    entryComponents: [FieldRESTJsonPathComponent],
    exports: [FieldRESTJsonPathComponent],
    providers: [ JSONPathService ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomFieldsJsonPathModule { }
