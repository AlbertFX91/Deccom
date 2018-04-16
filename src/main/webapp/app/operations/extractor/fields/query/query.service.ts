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

    dataRecover(req?: SQLDataRecover ): Observable<ResponseWrapper> {
        return this.http.post(this.resourceUrl + 'datarecover', req)
            .map((res: Response) => res);
    }

    private createRequesQueryOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
            params.set('username', req.username);
            params.set('password', req.password);
            params.set('url', req.url);
            params.set('query', req.query);
            options.params = params;
        }
        return options;
    }
}
