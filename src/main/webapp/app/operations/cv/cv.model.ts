export class CVCard {
    constructor(
        public id?: string,
        public logo_url?: string,
        public name?: string,
        public value?: string,
        public entries?: number,
        public creation_moment?: any,
        public last_update?: any,
        public status?: string
    ) {
    }
}
