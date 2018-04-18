import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions  } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SQLQuery, SQLDataRecover } from './query.model';
import { ResponseWrapper } from '../../../../shared';

@Injectable()
export class SQLQueryService {

    private resourceUrl = 'api/sql/';

    constructor(private http: Http) { }

    query(req?: SQLQuery): Observable<ResponseWrapper> {
        return this.http.post(this.resourceUrl + 'query', req)
            .map((res: Response) => res.json());
    }
}
