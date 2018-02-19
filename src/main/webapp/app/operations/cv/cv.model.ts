export class CVCard {
    constructor(
        public id?: string,
        public name?: string,
        public status?: string,
        public creationMoment?: any,
        public controlVarEntries?: number,
        public cvStyle?: CVStyle
    ) {
    }
}

export class CVStyle {
    constructor(
        public defaultName?: string,
        public icon?: string,
        public backgroundColor?: string,
        public fontColor?: string
    ) {
    }
}
