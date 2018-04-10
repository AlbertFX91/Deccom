import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[jhiField]',
})
export class FieldDirective {
  constructor(public viewContainerRef: ViewContainerRef) { }
}
