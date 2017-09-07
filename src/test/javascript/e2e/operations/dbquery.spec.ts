import { browser, element, by, $ } from 'protractor';

describe('DBQuery e2e test', () => {

    const username = element(by.id('username'));
    const password = element(by.id('password'));
    const operationMenu = element(by.id('operation-menu'));
    const accountMenu = element(by.id('account-menu'));
    const login = element(by.id('login'));
    const logout = element(by.id('logout'));

    const field_dbquery_username = element(by.id('field_dbquery_username'));
    const field_dbquery_password = element(by.id('field_dbquery_password'));
    const field_dbquery_url = element(by.id('field_dbquery_url'));
    const field_dbquery_query = element(by.id('field_dbquery_query'));

    beforeAll(() => {
        browser.get('/');

        accountMenu.click();
        login.click();

        username.sendKeys('admin');
        password.sendKeys('admin');
        element(by.css('button[type=submit]')).click();
        browser.waitForAngular();
    });

    it('should load DBQuery', () => {
        operationMenu.click();
        element.all(by.css('[routerLink="dbquery"]')).first().click().then(() => {
            const expectVal = /deccomApp.dbquery.home.title/;
            element.all(by.css('h2 span')).first().getAttribute('jhiTranslate').then((value) => {
                expect(value).toMatch(expectVal);
            });
        });
    });
    
    it('should get a result list from a query with properties', () => {
        field_dbquery_username.sendKeys('developer');
        field_dbquery_password.sendKeys('developer');
        field_dbquery_url.sendKeys('jdbc:mysql://localhost:3306/deccom');
        field_dbquery_query.sendKeys('SELECT * FROM AUTHOR');
        element(by.css('button.send-query')).click().then(() => {
            element.all(by.css('th')).count().then((num) => {
                expect(num).toBeGreaterThanOrEqual(1);
            });
        });
    });

    it('should get a result list from a query with rows', () => {
        element(by.css('button.send-query')).click().then(() => {
            element.all(by.css('td')).count().then((num) => {
                expect(num).toBeGreaterThanOrEqual(1);
            });
        });
    });

    it('should get a result detail from a query with one result with properties and values', () => {
        field_dbquery_username.clear()
        .then(()=>field_dbquery_password.clear())
        .then(()=>field_dbquery_password.clear())
        .then(()=>field_dbquery_url.clear())
        .then(()=>field_dbquery_query.clear())
        .then(()=>{
            field_dbquery_username.sendKeys('developer');
            field_dbquery_password.sendKeys('developer');
            field_dbquery_url.sendKeys('jdbc:mysql://localhost:3306/deccom');
            field_dbquery_query.sendKeys('SELECT * FROM AUTHOR WHERE IDAUTHOR=1');
            element(by.css('button.send-query')).click().then(() => {
                element.all(by.css('dt')).count().then((num_dt) => {
                    element.all(by.css('dd')).count().then((num_dd) => {
                        expect(num_dt).toEqual(num_dd);
                    });
                });
            });
        });
    });
  
    afterAll(() => {
        accountMenu.click();
        logout.click();
    });

});
