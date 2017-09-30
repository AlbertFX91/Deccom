import { browser, element, by, $ } from 'protractor';

describe('SQL e2e test', () => {

    const username = element(by.id('username'));
    const password = element(by.id('password'));
    const operationMenu = element(by.id('operation-menu'));
    const accountMenu = element(by.id('account-menu'));
    const login = element(by.id('login'));
    const logout = element(by.id('logout'));

    const field_sql_username = element(by.id('field_sql_username'));
    const field_sql_password = element(by.id('field_sql_password'));
    const field_sql_url = element(by.id('field_sql_url'));
    const field_sql_query = element(by.id('field_sql_query'));

    beforeAll(() => {
        browser.get('/');

        accountMenu.click();
        login.click();

        username.sendKeys('admin');
        password.sendKeys('admin');
        element(by.css('button[type=submit]')).click();
        browser.waitForAngular();
    });

    it('should load SQL', () => {
        operationMenu.click();
        element.all(by.css('[routerLink="sql"]')).first().click().then(() => {
            const expectVal = /deccomApp.sql.home.title/;
            element.all(by.css('h2 span')).first().getAttribute('jhiTranslate').then((value) => {
                expect(value).toMatch(expectVal);
            });
        });
    });
    
    it('should get a result list from a query with properties', () => {
        field_sql_username.sendKeys('developer');
        field_sql_password.sendKeys('developer');
        field_sql_url.sendKeys('jdbc:mysql://localhost:3306/deccom');
        field_sql_query.sendKeys('SELECT * FROM AUTHOR');
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
        field_sql_username.clear()
        .then(()=>field_sql_password.clear())
        .then(()=>field_sql_password.clear())
        .then(()=>field_sql_url.clear())
        .then(()=>field_sql_query.clear())
        .then(()=>{
            field_sql_username.sendKeys('developer');
            field_sql_password.sendKeys('developer');
            field_sql_url.sendKeys('jdbc:mysql://localhost:3306/deccom');
            field_sql_query.sendKeys('SELECT * FROM AUTHOR WHERE IDAUTHOR=1');
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
