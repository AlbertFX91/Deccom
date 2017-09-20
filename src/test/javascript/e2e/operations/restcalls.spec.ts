import { browser, element, by, $ } from 'protractor';

describe('API REST calls e2e test', () => {

    const username = element(by.id('username'));
    const password = element(by.id('password'));
    const entityMenu = element(by.id('entity-menu'));
    const accountMenu = element(by.id('account-menu'));
    const login = element(by.id('login'));
    const logout = element(by.id('logout'));
    const operationMenu = element(by.id('operation-menu'));

    beforeAll(() => {
        browser.get('/');

        accountMenu.click();
        login.click();

        username.sendKeys('admin');
        password.sendKeys('admin');
        element(by.css('button[type=submit]')).click();
        browser.waitForAngular();
    });

    it('should load API REST calls', () => {
        operationMenu.click();
        element.all(by.css('[routerLink="restcalls"]')).first().click().then(() => {
            const expectVal = /deccomApp.RestCalls.home.title/;
            element.all(by.css('h2 span')).first().getAttribute('jhiTranslate').then((value) => {
                expect(value).toMatch(expectVal);
            });
        });
    });

    it('should send a GET request for an array', () => {
        element(by.css('[id="field_url"]')).sendKeys("https://jsonplaceholder.typicode.com/posts");
        element(by.css('[type="submit"]')).click();

        var table = element.all(by.css('[class="table-responsive"]'));
        var cells = table.all(by.tagName('td'));

        expect(cells.count()).toBeLessThanOrEqual(400);
        expect(cells.count()).toBeGreaterThanOrEqual(400);
    });

    it('should send a GET request for a single JSON', () => {
        element(by.css('[id="field_url"]')).clear();
        element(by.css('[id="field_url"]')).sendKeys("https://jsonplaceholder.typicode.com/posts/1");
        element(by.css('[type="submit"]')).click();

        var container = element.all(by.css('[class="container"]'));
        var cells = container.all(by.tagName('dd'));

        expect(cells.count()).toBeLessThanOrEqual(8);
        expect(cells.count()).toBeGreaterThanOrEqual(8);
    });

    afterAll(() => {
        accountMenu.click();
        logout.click();
    });
});
