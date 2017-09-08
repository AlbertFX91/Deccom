/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DeccomTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RestCallsShowComponent } from '../../../../../../main/webapp/app/operations/restcalls/restcalls-show.component';
import { RestCallsService } from '../../../../../../main/webapp/app/operations/restcalls/restcalls.service';

describe('Component Tests', () => {

    describe('Game Management Detail Component', () => {
        let comp: RestCallsShowComponent;
        let fixture: ComponentFixture<RestCallsShowComponent>;
        let service: RestCallsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DeccomTestModule],
                declarations: [RestCallsShowComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({ id: 123 })
                    },
                    RestCallsService,
                    JhiEventManager
                ]
            }).overrideTemplate(RestCallsShowComponent, '')
                .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RestCallsShowComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RestCallsService);
        });

        describe('OnInit', () => {
            it('Should load an array with one JSON', () => {
                // GIVEN
                comp.response = [{"id":1,"title":"sunt aut facere repellat provident occaecati excepturi optio reprehenderit","body":"quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto","userId":1}];

                // WHEN
                const onSuccess = comp.onSuccess();

                // THEN
                expect(onSuccess).not.toBeNull();
            });
        });

    });

});
