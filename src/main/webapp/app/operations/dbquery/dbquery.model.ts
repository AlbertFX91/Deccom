export class DBQuery {
    constructor(
        public username?: string,
        public password?: string,
        public url?: string,
        public query?: string,
    ) {
    }
}

export class DBResponse {
    constructor(
        public metadata?: DBMetadata,
        public data?: any,
    ) {
    }
}
export class DBMetadata {
    constructor(
        public tables?: String[],
        public fields?: DBField[],
    ) {
    }
}

export class DBField {
    constructor(
        public name?: String,
        public isPrimaryKey?: Boolean,
    ) {
    }
}
