import { CVStyle } from '../cv/cv.model';

export class ExtractorItem {
    constructor(
        public extractorClass?: string,
        public style?: CVStyle,
        public uid?: string
    ) {
    }
}

export class DeccomField {
    constructor(
        public name?: string,
        public type?: string,
        public component?: string
    ) {
    }
}

export class ExtractorNew {
    constructor(
        public extractor?: ExtractorItem,
        public fields?: DeccomField[]
    ) {
    }
}
