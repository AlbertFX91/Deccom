export class RESTDataRecover {
    constructor(
        public query?: string,
        public controlVarName?: string,
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
