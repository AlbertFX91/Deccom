import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DeccomSQLModule } from './sql/sql.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */
import { DeccomRestCallsModule } from './restcalls/restcalls.module';

@NgModule({
    imports: [
        DeccomSQLModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
        DeccomRestCallsModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomOperationModule {}
