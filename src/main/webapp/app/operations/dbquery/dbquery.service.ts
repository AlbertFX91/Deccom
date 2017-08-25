import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { DBQuery } from './dbquery.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DBQueryService {

    private resourceUrl = 'api/db/';

    constructor(private http: Http) { }
    query(req?: DBQuery): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUrl + 'query', req)
            .map((res: Response) => res.json());
    }
}
