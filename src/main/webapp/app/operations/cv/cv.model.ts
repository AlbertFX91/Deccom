import { ExtractorItem } from '../extractor/extractor.model'

export class CV {
    constructor(
        public id?: string,
        public name?: string,
        public status?: string,
        public creationMoment?: any,
        public controlVarEntries?: any,
        public extractor?: ExtractorItem
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
