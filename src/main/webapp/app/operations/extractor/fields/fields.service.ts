import { Injectable, Component } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions  } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper } from '../../../shared';

import { FieldRESTJsonPathComponent } from './jsonpath/jsonpath.component';

import {fieldMap} from './fields.map'

@Injectable()
export class FieldService {

    constructor(private http: Http) { }

    get(id: string): any {
        return fieldMap[id];
    }
}
