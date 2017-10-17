import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { CVCard, CVCardList } from './cv.model';

@Injectable()
export class CVService {

    private resourceUrl = 'api/cv/';

    cvCardLists: CVCardList[];

    constructor(private http: Http) {
        const cvCardLists: CVCardList[] = [
            {
                name: 'deccomApp.ControlVar.running',
                cvCards: [
                    {
                        'id': 'cvCardId1',
                        'logo_url': 'https://cdn-images-1.medium.com/max/599/1*uHzooF1EtgcKn9_XiSST4w.png',
                        'name': 'Followers',
                        'value': '160',
                        'num_entries': 1,
                        'frequency': '10 days',
                        'last_update': '05/05/2017 10:30',
                        'status': 'Running'
                    }
                ]
            },
            {
                name: 'deccomApp.ControlVar.paused',
                cvCards: []
            },
            {
                name: 'deccomApp.ControlVar.blocked',
                cvCards: []
            }
        ]
        this.cvCardLists = cvCardLists;
    }

    getCards() {
        return this.cvCardLists;
    }

    getCard(cvCardId: string) {
        return this.cvCardLists[cvCardId];
    }

}
