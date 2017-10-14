/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { DeccomTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SQLResultComponent } from '../../../../../../main/webapp/app/operations/sql/sql-result.component';
import { SQLService } from '../../../../../../main/webapp/app/operations/sql/sql.service';
import { SQLQuery } from '../../../../../../main/webapp/app/operations/sql/sql.model';
import { Principal } from '../../../../../../main/webapp/app/shared';

describe('Component Tests', () => {

    describe('SQL Result Component', () => {
        let comp: SQLResultComponent;
        let fixture: ComponentFixture<SQLResultComponent>;
        let service: SQLService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DeccomTestModule],
                declarations: [SQLResultComponent],
                providers: [
                    JhiDateUtils,
                    SQLService,
                    JhiEventManager,
                ]
            }).overrideTemplate(SQLResultComponent, '')
                .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SQLResultComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SQLService);
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
