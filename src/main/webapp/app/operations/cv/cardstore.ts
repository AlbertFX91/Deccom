/*
import { CVCard } from './cv.model';

export class CardStore {

    cvCards: Object = {};
    lastId = -1;

    _addCard(cvCard: CVCard) {
        cvCard.id = String(++this.lastId);
        this.cvCards[cvCard.id] = cvCard;
        return (cvCard.id);
    }

    getCard(cvCardId: string) {
        return this.cvCards[cvCardId];
    }

    newCard(name: string): string {
        const cvCard = new CVCard();
        return (this._addCard(cvCard));
    }

}

*/