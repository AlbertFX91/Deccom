import { CVStyle } from '../cv/cv.model';

export class ExtractorItem {
    constructor(
        public extractorClass?: string,
        public style?: CVStyle
    ) {
    }
}
