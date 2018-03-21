import {FieldRESTJsonPathComponent} from './jsonpath/jsonpath.component';
import {FieldSQLQueryComponent} from './query/query.component';

export const fieldMap = {
    'rest.jsonPath.field': FieldRESTJsonPathComponent,
    'sql.query.field': FieldSQLQueryComponent,
};

export const components = function(){
    return Object.keys(fieldMap).map(function(key){
        return fieldMap[key];
    });
}
