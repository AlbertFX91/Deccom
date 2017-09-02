/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DeccomTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { APIRestCallsShowComponent } from '../../../../../../main/webapp/app/operations/apirestcalls/apirestcalls-show.component';
import { APIRestCallsService } from '../../../../../../main/webapp/app/operations/apirestcalls/apirestcalls.service';

describe('Component Tests', () => {

    describe('Game Management Detail Component', () => {
        let comp: APIRestCallsShowComponent;
        let fixture: ComponentFixture<APIRestCallsShowComponent>;
        let service: APIRestCallsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DeccomTestModule],
                declarations: [APIRestCallsShowComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({ id: 123 })
                    },
                    APIRestCallsService,
                    JhiEventManager
                ]
            }).overrideTemplate(APIRestCallsShowComponent, '')
                .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(APIRestCallsShowComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(APIRestCallsService);
        });

        describe('OnInit', () => {
            it('Should load an array with one JSON', () => {
                // GIVEN
                spyOn(service, 'noMapping').and.returnValue((Observable.of('[{"id":1,"title":"sunt aut facere repellat provident occaecati excepturi optio reprehenderit","body":"quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto","userId":1}]')));
                // comp.response = [{"id":1,"title":"sunt aut facere repellat provident occaecati excepturi optio reprehenderit","body":"quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto","userId":1}];

                // WHEN
                comp.ngOnInit();
                // const onSuccess = comp.onSuccess();

                // THEN
                expect(service.noMapping).toHaveBeenCalledWith('https://jsonplaceholder.typicode.com/posts/1');
                // expect(onSuccess.indexOf('id')).toBeGreaterThanOrEqual(1);
            });
        });

    });

});
