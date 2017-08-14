/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DeccomTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AcmeDetailComponent } from '../../../../../../main/webapp/app/entities/acme/acme-detail.component';
import { AcmeService } from '../../../../../../main/webapp/app/entities/acme/acme.service';
import { Acme } from '../../../../../../main/webapp/app/entities/acme/acme.model';

describe('Component Tests', () => {

    describe('Acme Management Detail Component', () => {
        let comp: AcmeDetailComponent;
        let fixture: ComponentFixture<AcmeDetailComponent>;
        let service: AcmeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DeccomTestModule],
                declarations: [AcmeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AcmeService,
                    JhiEventManager
                ]
            }).overrideTemplate(AcmeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AcmeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AcmeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Acme('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.acme).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
