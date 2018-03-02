import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */
import { DeccomSQLModule } from './sql/sql.module';
import { DeccomRESTModule } from './rest/rest.module';
import { DeccomCVModule } from './cv/cv.module';
import { DeccomTwitterModule } from './twitter/twitter.module';
import { DeccomFacebookModule } from './facebook/facebook.module';
import { DeccomExtractorModule } from './extractor/extractor.module';
import { DeccomEventModule } from './event/event.module';

@NgModule({
    imports: [
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
        DeccomSQLModule,
        DeccomRESTModule,
        DeccomCVModule,
        DeccomTwitterModule,
        DeccomFacebookModule,
        DeccomExtractorModule,
        DeccomEventModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomOperationModule {}
