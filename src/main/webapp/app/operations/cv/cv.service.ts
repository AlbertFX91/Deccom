import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { CV, NewCV } from './cv.model';
import { JhiDateUtils } from 'ng-jhipster';

@Injectable()
export class CVService {

    private resourceUrl = 'api/controlvar/';

    cvCards: CV[];

    constructor(private http: Http, private dateUtils: JhiDateUtils) {
        this.cvCards = [];
    }
    /*
    this.cvCards = [
        {
            'id': 'cvCardId1',
            'logo_url': 'http://www.iconhot.com/icon/png/coded/512/sql-1.png',
            'name': 'Followers',
            'value': '160',
            'entries': 1,
            'creation_moment': '02/02/2017 8:15',
            'last_update': '05/05/2017 10:30',
            'status': 'Running'
        },
        {
            'id': 'cvCardId2',
            'logo_url': 'https://www.vera.com/wp-content/uploads/2016/04/apps-sdk_03_rest_api_275.png',
            'name': 'Following',
            'value': '100',
            'entries': 1,
            'creation_moment': '01/01/2016 10:15',
            'last_update': '05/05/2017 11:30',
            'status': 'Running'
        },
        {
            'id': 'cvCardId3',
            'logo_url': 'https://www.vera.com/wp-content/uploads/2016/04/apps-sdk_03_rest_api_275.png',
            'name': 'Friends',
            'value': '10',
            'entries': 1,
            'creation_moment': '10/10/2016 8:45',
            'last_update': '11/11/2016 12:30',
            'status': 'Paused'

        },
        {
            'id': 'cvCardId4',
            'logo_url': 'http://www.freeiconspng.com/uploads/images-facebook-f-logo-png-transparent-background-page-2-29.png',
            'name': 'Contacts',
            'value': '40',
            'entries': 3,
            'creation_moment': '02/02/2014 8:15',
            'last_update': '12/12/2015 9:30',
            'status': 'Blocked'
        }
    ]
    */

   create(cv: NewCV): Observable<NewCV> {
    return this.http.post(this.resourceUrl, cv).map((res: Response) => {
        const jsonResponse = res.json();
        return jsonResponse;
    });
}

    findAll(pageSettings: any): Observable<ResponseWrapper> {
        const options = this.createRequestOption(pageSettings);
        return this.http.get(this.resourceUrl + 'all', options)
            .map((res: Response) => res);
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.publication_date = this.dateUtils
            .convertLocalDateFromServer(entity.publication_date);
    }

    public createRequestOption(pageSettings: any): BaseRequestOptions {
        const options: BaseRequestOptions = createRequestOption(pageSettings);
        // const params: URLSearchParams = new URLSearchParams();
        // options.params = params;
        return options;
    }
}
