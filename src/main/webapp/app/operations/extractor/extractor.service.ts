import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions  } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ExtractorNew } from './extractor.model';
import { ResponseWrapper } from '../../shared';

@Injectable()
export class ExtractorService {

    private resourceUrl = 'api/extractor/';

    constructor(private http: Http) { }

    all(): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUrl).map((res: Response) => this.convertResponse(res));
    }

    find(id: string): Observable<ExtractorNew> {
        return this.http.get(`${this.resourceUrl}/fields/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
}
