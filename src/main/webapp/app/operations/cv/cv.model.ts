export class CVCard {
    constructor(
        public id?: string,
        public logo_url?: string,
        public name?: string,
        public value?: string,
        public num_entries?: number,
        public frequency?: string,
        public last_update?: any,
        public status?: string
    ) {
    }
}

export class CVCardList {
    constructor(
        name?: string,
        cvCards?: string[]
    ) {
    }
}
