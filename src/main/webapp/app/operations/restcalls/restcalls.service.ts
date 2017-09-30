import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RestCallsService {

    private resourceUrl = 'api/restcalls/';

    constructor(private http: Http) { }

    noMapping(url: string, pageSettings: any): Observable<ResponseWrapper> {
        const options = this.createRequestOption(url, pageSettings);
        return this.http.get(this.resourceUrl + 'nomapping', options)
            .map((res: Response) => res);
    }

    private createRequestOption(url: string, pageSettings: any): BaseRequestOptions {
        const options: BaseRequestOptions = createRequestOption(pageSettings);
        // const params: URLSearchParams = new URLSearchParams();
        options.params.set('url', url);
        // options.params = params;
        return options;
    }

}
