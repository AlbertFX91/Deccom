import { Component, OnInit } from '@angular/core';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { FieldBaseComponent } from '../fields.component';
import { JSONPathService } from './jsonpath.service';
@Component({
  selector: 'jhi-rest-jsonpath',
  templateUrl: './jsonpath.component.html',
  styleUrls: ['./jsonpath.component.css'],
})
export class FieldRESTJsonPathComponent extends FieldBaseComponent implements OnInit {

  url: string;
  isSaving: boolean;
  json: any;
  path: string;

  constructor(
    public jsonPathService: JSONPathService,
    private parseLinks: JhiParseLinks,
    private alertService: JhiAlertService,
    private eventManager: JhiEventManager
  ) {
    super();
  }

  ngOnInit() {
    this.isSaving = false;
    this.json = [];
    this.url = this.getValue('url');
  }

  onClose() {
    if (this.path) {
      this.setValue(this.path);
      this.setValueByAttr('url', this.url);
      this.finish();
    }
  }
  createPath(path: string) {
    this.path = path;
    this.setValue(this.path);
  }

  onSubmit() {
    this.save();
  }

  save() {
    this.isSaving = true
    this.jsonPathService.noMapping(this.url, {}).subscribe(
      (data: any) => this.onSuccess(data.json(), data.headers),
      (error: Response) => this.onJSONError(error)
    )
  }
  onJSONError(error) {
    try {
      error.json();
    } catch (exception) {
      error.message = error.text();
    }
    this.isSaving = false;
    this.onError(error);
  }

  private onError(error) {
    this.alertService.error(error.message, null, null);
  }

  onSuccess(data: any, headers: any) {
    this.isSaving = false;
    this.json = data;
  }
}
