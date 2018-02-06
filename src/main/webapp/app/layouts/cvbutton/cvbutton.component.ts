import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { LoginService } from '../../shared';

@Component({
    selector: 'jhi-cvbutton',
    templateUrl: './cvbutton.component.html',
    styleUrls: [
        'cvbutton.css'
    ]
})
export class CVButtonComponent implements OnInit {

    constructor(
        private router: Router,
        private loginService: LoginService,
    ) {}

    ngOnInit() {

    }
}
