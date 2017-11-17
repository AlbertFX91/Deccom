import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */
import { DeccomSQLModule } from './sql/sql.module';
import { DeccomRESTModule } from './rest/rest.module';
import { DeccomCVModule } from './cv/cv.module';
import { DeccomTwitterModule } from './twitter/twitter.module';

@NgModule({
    imports: [
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
        DeccomSQLModule,
        DeccomRESTModule,
        DeccomCVModule,
        DeccomTwitterModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomOperationModule {}
