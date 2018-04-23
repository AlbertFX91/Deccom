/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { DeccomTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FieldSQLQueryResultComponent } from '../../../../../../main/webapp/app/operations/extractor/fields/query/query-result.component';
import { SQLQueryService } from '../../../../../../main/webapp/app/operations/extractor/fields/query/query.service';
import { SQLQuery } from '../../../../../../main/webapp/app/operations/extractor/fields/query/query.model';
import { Principal } from '../../../../../../main/webapp/app/shared';

describe('Component Tests', () => {

    describe('SQL Result Component', () => {
        let comp: FieldSQLQueryResultComponent;
        let fixture: ComponentFixture<FieldSQLQueryResultComponent>;
        let service: SQLQueryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DeccomTestModule],
                declarations: [SQLQueryService],
                providers: [
                    JhiDateUtils,
                    SQLQueryService,
                    JhiEventManager,
                ]
            }).overrideTemplate(FieldSQLQueryResultComponent, '')
                .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FieldSQLQueryResultComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SQLQueryService);
        });

        describe('properties', () => {
            it('Should load properties with an array as DBResponse', () => {
                // GIVEN
                comp.sqlResponse = {
                    'data': [{ 'id': 1, 'name': 'nametest1' },
                    { 'id': 2, 'name': 'nametest2' }],
                    'metadata': {
                        'tables': ["users"],
                        'fields': [
                            {
                                'name': 'id',
                                'isPrimaryKey': true
                            },
                            {
                                'name': 'name',
                                'isPrimaryKey': false
                            }
                        ]
                    }
                };

                // WHEN
                const fields = comp.fields();

                // THEN
                expect(fields[0].name).toBe('id');
                expect(fields[1].name).toBe('name');
            });

        });
    });

});
