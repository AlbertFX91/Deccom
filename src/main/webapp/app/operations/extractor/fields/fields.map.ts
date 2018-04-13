import {FieldRESTJsonPathComponent} from './jsonpath/jsonpath.component';
import {FieldSQLQueryComponent} from './query/query.component';

import { DeccomFieldsJsonPathModule } from './jsonpath/jsonpath.module'
import { DeccomFieldsSQLQueryModule } from './query/query.module'

export const fieldMap = {
    'rest.jsonPath.field': FieldRESTJsonPathComponent,
    'sql.query.field': FieldSQLQueryComponent,
};

export const moduleMap = {
    'rest.jsonPath.field': DeccomFieldsJsonPathModule,
    'sql.query.field': DeccomFieldsSQLQueryModule,
}

export const components = function(){
    return Object.keys(fieldMap).map(function(key){
        return fieldMap[key];
    });
}

export const modules = function(){
    return Object.keys(fieldMap).map(function(key){
        return moduleMap[key];
    });
}
