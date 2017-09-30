import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DeccomDBQueryModule } from './dbquery/dbquery.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */
import { DeccomRESTModule } from './rest/rest.module';

@NgModule({
    imports: [
        DeccomDBQueryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
        DeccomRESTModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomOperationModule {}
