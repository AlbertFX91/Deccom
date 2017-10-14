export class SQLQuery {
    constructor(
        public username?: string,
        public password?: string,
        public url?: string,
        public query?: string,
    ) {
    }
}

export class SQLResponse {
    constructor(
        public metadata?: SQLMetadata,
        public data?: any,
    ) {
    }
}
export class SQLMetadata {
    constructor(
        public tables?: string[],
        public fields?: SQLField[],
    ) {
    }
}

export class SQLField {
    constructor(
        public name?: string,
        public isPrimaryKey?: Boolean,
    ) {
    }
}

export class SQLConnection {
    constructor(
        public username?: string,
        public password?: string,
        public url?: string
    ) {
    }
}

export class SQLDataRecover {
    constructor(
        public query?: string,
        public controlVarName?: string,
        public connection?: SQLConnection
    ) {
    }
}
