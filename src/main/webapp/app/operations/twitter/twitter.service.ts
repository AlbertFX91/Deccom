import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { RESTService } from '../rest/rest.service';
import { RESTDataRecover } from '../rest/rest.model';

@Injectable()
export class TwitterService {

    private resourceUrl = 'api/twitter/';

    constructor(
        private http: Http,
        private restService: RESTService
    ) { }

    noMapping(url: string, pageSettings: any): Observable<ResponseWrapper> {
        const options = this.restService.createRequestOption(url, pageSettings);
        return this.http.get(this.resourceUrl + 'nomapping', options)
            .map((res: Response) => res);
    }

}
