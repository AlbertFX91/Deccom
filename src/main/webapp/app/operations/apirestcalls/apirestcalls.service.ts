import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper } from '../../shared';

@Injectable()
export class APIRestCallsService {

    private resourceUrl = 'api/apirestcalls/';

    constructor(private http: Http) { }

    noMapping(url: string): Observable<ResponseWrapper> {
        const options = this.createRequestOption(url);
        return this.http.get(this.resourceUrl + 'nomapping', options)
            .map((res: Response) => res.json());
    }

    private createRequestOption(url: string): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        const params: URLSearchParams = new URLSearchParams();
        params.set('url', url);
        options.params = params;
        return options;
    }

}
