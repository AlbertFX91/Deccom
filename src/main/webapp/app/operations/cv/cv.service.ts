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

    public createRequestOption(pageSettings: any): BaseRequestOptions {
        const options: BaseRequestOptions = createRequestOption(pageSettings);
        // const params: URLSearchParams = new URLSearchParams();
        // options.params = params;
        return options;
    }
}
