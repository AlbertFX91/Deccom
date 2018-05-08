import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { CV, NewCV } from './cv.model';
import { JhiDateUtils } from 'ng-jhipster';

@Injectable()
export class CVService {

    private resourceUrl = 'api/controlvar/';

    cvCards: CV[];

    constructor(private http: Http, private dateUtils: JhiDateUtils) {
        this.cvCards = [];
    }

    create(cv: NewCV): Observable<NewCV> {
        return this.http.post(this.resourceUrl, cv).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    find(id: string): Observable<CV> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(cv: CV): Observable<CV> {
        return this.http.put(this.resourceUrl + '/general/', cv).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findAll(pageSettings: any): Observable<ResponseWrapper> {
        const options = this.createRequestOption(pageSettings);
        return this.http.get(this.resourceUrl + 'all', options)
            .map((res: Response) => res);
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    restart(id: string): Observable<Response> {
        return this.http.get(this.resourceUrl + 'restart', this.createGetRequest(id));
    }

    pause(id: string): Observable<Response> {
        return this.http.get(this.resourceUrl + 'pause', this.createGetRequest(id));
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

    private createRequestOption(pageSettings: any): BaseRequestOptions {
        const options: BaseRequestOptions = createRequestOption(pageSettings);
        // const params: URLSearchParams = new URLSearchParams();
        // options.params = params;
        return options;
    }

    public createGetRequest(id: string): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        const params: URLSearchParams = new URLSearchParams();
        params.set('controlVarId', id);
        options.params = params;
        return options;
    }
}
