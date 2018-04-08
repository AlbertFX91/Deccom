import { Component, OnInit } from '@angular/core';
import { FieldBaseComponent } from '../fields.component';

@Component({
  selector: 'jhi-rest-jsonpath',
  templateUrl: './jsonpath.component.html',
  styleUrls: ['./jsonpath.component.css'],
})
export class FieldRESTJsonPathComponent extends FieldBaseComponent implements OnInit {

  constructor() {
    super();
  }

  ngOnInit() {
  }

  onClick() {
    this.setValue('Hello World from jsonpath component!');
  }
}
