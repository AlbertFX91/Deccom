import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[jhiExtractor]',
})
export class ExtractorDirective {
  constructor(public viewContainerRef: ViewContainerRef) { }
}
