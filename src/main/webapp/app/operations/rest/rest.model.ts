export class RESTDataRecover {
    constructor(
        public query?: string,
        public controlVarName?: string,
        public frequency_sec?: number,
        public restConnection?: RESTConnection
    ) {
    }
}

export class RESTConnection {
    constructor(
        public url?: string
    ) {
    }
}
