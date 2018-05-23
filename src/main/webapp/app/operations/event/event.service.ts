import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { Event } from './event.model';
import { JhiDateUtils } from 'ng-jhipster';

@Injectable()
export class EventService {

    private resourceUrl = 'api/event';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    findOne(id: string): Observable<Event> {
        return this.http.get(`${this.resourceUrl + '/findOne'}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findAll(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl + '/findAll', options)
            .map((res: Response) => this.convertResponse(res));
    }

    dates(startingDate: string, endingDate: string, req?: any): Observable<ResponseWrapper> {
        const options = this.createRequestOptionDates(startingDate, endingDate, req);
        return this.http.get(this.resourceUrl + '/dates', options)
            .map((res: Response) => this.convertResponse(res));
    }

    create(req?: Event): Observable<ResponseWrapper> {
        return this.http.post(this.resourceUrl + '/create', req)
            .map((res: Response) => res);
    }

    update(event: Event): Observable<Event> {
        const copy = this.convert(event);
        return this.http.put(this.resourceUrl + '/update', copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    delete(id: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl + '/delete'}/${id}`);
    }

    public createRequestOption(pageSettings: any): BaseRequestOptions {
        const options: BaseRequestOptions = createRequestOption(pageSettings);
        // const params: URLSearchParams = new URLSearchParams();
        // options.params = params;
        return options;
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.creationMoment = this.dateUtils
            .convertDateTimeFromServer(entity.creationMoment);
        entity.startingDate = this.dateUtils
            .convertLocalDateFromServer(entity.startingDate);
        if (entity.endingDate) {
            entity.endingDate = this.dateUtils
                .convertLocalDateFromServer(entity.endingDate);
        }
    }

    private convert(event: Event): Event {
        const copy: Event = Object.assign({}, event);
        copy.creationMoment = this.dateUtils
            .convertDateTimeFromServer(event.creationMoment);
        copy.startingDate = this.dateUtils
            .convertLocalDateToServer(event.startingDate);
        if (copy.endingDate) {
            copy.endingDate = this.dateUtils
                .convertLocalDateToServer(event.endingDate);
        }
        return copy;
    }

    public createRequestOptionDates(startingDate: string, endingDate: string, pageSettings: any): BaseRequestOptions {
        const options: BaseRequestOptions = createRequestOption(pageSettings);
        // const params: URLSearchParams = new URLSearchParams();
        options.params.set('startingDate', startingDate);
        options.params.set('endingDate', endingDate);
        // options.params = params;
        return options;
    }

}
