import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Acme } from '../../entities/acme/acme.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DBQueryService {

    private resourceUrl = 'api/acmes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(acme: Acme): Observable<Acme> {
        const copy = this.convert(acme);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(acme: Acme): Observable<Acme> {
        const copy = this.convert(acme);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: string): Observable<Acme> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
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

    private convert(acme: Acme): Acme {
        const copy: Acme = Object.assign({}, acme);
        copy.publication_date = this.dateUtils
            .convertLocalDateToServer(acme.publication_date);
        return copy;
    }
}
