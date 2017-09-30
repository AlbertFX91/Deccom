import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Ng2Webstorage } from 'ng2-webstorage';

import { DeccomSharedModule, UserRouteAccessService } from './shared';
import { DeccomHomeModule } from './home/home.module';
import { DeccomAdminModule } from './admin/admin.module';
import { DeccomAccountModule } from './account/account.module';
import { DeccomEntityModule } from './entities/entity.module';
import { DeccomOperationModule } from './operations/operation.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        DeccomSharedModule,
        DeccomHomeModule,
        DeccomAdminModule,
        DeccomAccountModule,
        DeccomEntityModule,
        DeccomOperationModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class DeccomAppModule {}
