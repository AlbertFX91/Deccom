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
        public tables?: String[],
        public fields?: SQLField[],
    ) {
    }
}

export class SQLField {
    constructor(
        public name?: String,
        public isPrimaryKey?: Boolean,
    ) {
    }
}
