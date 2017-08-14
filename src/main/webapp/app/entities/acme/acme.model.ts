import { BaseEntity } from './../../shared';

export class Acme implements BaseEntity {
    constructor(
        public id?: string,
        public title?: string,
        public description?: string,
        public publication_date?: any,
        public rating?: number,
    ) {
    }
}
