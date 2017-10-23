import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { CVCard } from './cv.model';

@Injectable()
export class CVService {

    private resourceUrl = 'api/cv/';

    cvCards: CVCard[];

    constructor(private http: Http) {
        this.cvCards = [
            {
                'id': 'cvCardId1',
                'logo_url': 'https://cdn-images-1.medium.com/max/599/1*uHzooF1EtgcKn9_XiSST4w.png',
                'name': 'Followers',
                'value': '160',
                'num_entries': 1,
                'frequency': '10 days',
                'last_update': '05/05/2017 10:30',
                'status': 'Running'
            },
            {
                'id': 'cvCardId2',
                'logo_url': 'https://cdn-images-1.medium.com/max/599/1*uHzooF1EtgcKn9_XiSST4w.png',
                'name': 'Following',
                'value': '100',
                'num_entries': 1,
                'frequency': '20 days',
                'last_update': '05/05/2017 10:30',
                'status': 'Running'
            },
            {
                'id': 'cvCardId3',
                'logo_url': 'https://cdn-images-1.medium.com/max/599/1*uHzooF1EtgcKn9_XiSST4w.png',
                'name': 'Friends',
                'value': '10',
                'num_entries': 1,
                'frequency': '20 days',
                'last_update': '11/11/2016 10:30',
                'status': 'Paused'

            },
            {
                'id': 'cvCardId4',
                'logo_url': 'https://cdn-images-1.medium.com/max/599/1*uHzooF1EtgcKn9_XiSST4w.png',
                'name': 'Contacts',
                'value': '40',
                'num_entries': 3,
                'frequency': '8 days',
                'last_update': '12/12/2015 10:30',
                'status': 'Blocked'
            }
        ]
    }

    getCards() {
        return this.cvCards;
    }

    getRunningCards() {
        return this.filterCardsByStatus('Running');
    }

    getPausedCards() {
        return this.filterCardsByStatus('Paused');
    }

    getBlockedCards() {
        return this.filterCardsByStatus('Blocked');
    }

    filterCardsByStatus(status: String) {
        return this.cvCards.filter((cvCard) => cvCard.status === status);
    }

}
