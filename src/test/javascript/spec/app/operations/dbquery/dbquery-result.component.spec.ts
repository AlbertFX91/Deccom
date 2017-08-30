/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { DeccomTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DBQueryResultComponent } from '../../../../../../main/webapp/app/operations/dbquery/dbquery-result.component';
import { DBQueryService } from '../../../../../../main/webapp/app/operations/dbquery/dbquery.service';
import { DBQuery } from '../../../../../../main/webapp/app/operations/dbquery/dbquery.model';
import { Principal } from '../../../../../../main/webapp/app/shared';

describe('Component Tests', () => {

    describe('DBQuery Result Component', () => {
        let comp: DBQueryResultComponent;
        let fixture: ComponentFixture<DBQueryResultComponent>;
        let service: DBQueryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DeccomTestModule],
                declarations: [DBQueryResultComponent],
                providers: [
                    JhiDateUtils,
                    DBQueryService,
                    JhiEventManager,
                ]
            }).overrideTemplate(DBQueryResultComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DBQueryResultComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DBQueryService);
        });

        describe('properties', () => {
            it('Should load properties with an array as queryResult', () => {
                // GIVEN
                comp.queryResult = [{'id': 1, 'name': 'nametest1', 'surname': 'surnametest1'},
                {'id': 2, 'name': 'nametest2', 'surname': 'surnametest2'}];

                // WHEN
                const properties = comp.properties();

                // THEN
                expect(properties.indexOf('id')).toBeGreaterThan(-1);
                expect(properties.indexOf('name')).toBeGreaterThan(-1);
                expect(properties.indexOf('surname')).toBeGreaterThan(-1);
            });

            it('Should load properties with one element as queryResult', () => {
                // GIVEN
                comp.queryResult = {'id': 1, 'name': 'nametest1', 'surname': 'surnametest1'}

                // WHEN
                const properties = comp.properties();

                // THEN
                expect(properties.indexOf('id')).toBeGreaterThan(-1);
                expect(properties.indexOf('name')).toBeGreaterThan(-1);
                expect(properties.indexOf('surname')).toBeGreaterThan(-1);
            });

            it('Should load properties sorted', () => {
                // GIVEN
                comp.queryResult = {'id': 1, 'name': 'nametest1', 'surname': 'surnametest1'}

                // WHEN
                const properties = comp.properties();

                // THEN
                expect(properties.indexOf('id')).toBe(0);
                expect(properties.indexOf('name')).toBe(1);
                expect(properties.indexOf('surname')).toBe(2);
            });
        });
    });

});
