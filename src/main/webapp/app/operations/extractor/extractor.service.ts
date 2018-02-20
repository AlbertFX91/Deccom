import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions  } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ExtractorItem } from './extractor.model';
import { ResponseWrapper } from '../../shared';

@Injectable()
export class ExtractorService {

    private resourceUrl = 'api/extractor/';

    constructor(private http: Http) { }

    all(): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUrl).map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
}
