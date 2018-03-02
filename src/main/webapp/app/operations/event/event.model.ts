import { BaseEntity } from './../../shared';

export class Event {
    constructor(
        public id?: string,
        public name?: string,
        public creationMoment?: any,
        public startingDate?: any,
        public endingDate?: any
    ) {
    }
}
