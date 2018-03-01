import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { Event } from './event.model';
import { JhiDateUtils } from 'ng-jhipster';

@Injectable()
export class EventService {

    private resourceUrl = 'api/event/';
    events: Event[];

    constructor(private http: Http, private dateUtils: JhiDateUtils) {
        this.events = [];
    }

    findAll(pageSettings: any): Observable<ResponseWrapper> {
        const options = this.createRequestOption(pageSettings);
        return this.http.get(this.resourceUrl + 'findAll', options)
            .map((res: Response) => res);
    }

    create(req?: Event): Observable<ResponseWrapper> {
        return this.http.post(this.resourceUrl + 'create', req)
            .map((res: Response) => res);
    }

    delete(id: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl + '/delete'}/${id}`);
        // return this.http.delete(this.resourceUrl + 'delete/' + id);
    }

    public createRequestOption(pageSettings: any): BaseRequestOptions {
        const options: BaseRequestOptions = createRequestOption(pageSettings);
        // const params: URLSearchParams = new URLSearchParams();
        // options.params = params;
        return options;
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.publication_date = this.dateUtils
            .convertLocalDateFromServer(entity.publication_date);
    }

}
