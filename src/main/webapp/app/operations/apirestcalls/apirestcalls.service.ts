import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper } from '../../shared';

@Injectable()
export class APIRestCallsService {

    private resourceUrl = 'api/apirestcalls/';

    constructor(private http: Http) { }

    query(url: string): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUrl + 'nomapping', url)
            .map((res: Response) => res.json());
    }

}
