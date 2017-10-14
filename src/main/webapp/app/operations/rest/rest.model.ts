export class RESTDataRecover {
    constructor(
        public query?: String,
        public controlVarName?: String,
        public restConnection?: RESTConnection
    ) {
    }
}

export class RESTConnection {
    constructor(
        public url?: String
    ) {
    }
}
