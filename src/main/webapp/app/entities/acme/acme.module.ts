import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeccomSharedModule } from '../../shared';
import {
    AcmeService,
    AcmePopupService,
    AcmeComponent,
    AcmeDetailComponent,
    AcmeDialogComponent,
    AcmePopupComponent,
    AcmeDeletePopupComponent,
    AcmeDeleteDialogComponent,
    acmeRoute,
    acmePopupRoute,
} from './';

const ENTITY_STATES = [
    ...acmeRoute,
    ...acmePopupRoute,
];

@NgModule({
    imports: [
        DeccomSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AcmeComponent,
        AcmeDetailComponent,
        AcmeDialogComponent,
        AcmeDeleteDialogComponent,
        AcmePopupComponent,
        AcmeDeletePopupComponent,
    ],
    entryComponents: [
        AcmeComponent,
        AcmeDialogComponent,
        AcmePopupComponent,
        AcmeDeleteDialogComponent,
        AcmeDeletePopupComponent,
    ],
    providers: [
        AcmeService,
        AcmePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeccomAcmeModule {}
