import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions  } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DBQuery } from './dbquery.model';
import { ResponseWrapper } from '../../shared';

@Injectable()
export class DBQueryService {

    private resourceUrl = 'api/db/';

    constructor(private http: Http) { }

    query(req?: DBQuery): Observable<ResponseWrapper> {
        const options = this.createRequesQueryOption(req);
        return this.http.get(this.resourceUrl + 'query', options)
            .map((res: Response) => res.json());
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
