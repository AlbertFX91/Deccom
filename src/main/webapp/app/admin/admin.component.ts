import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProfileService } from '../layouts/profiles/profile.service';

@Component({
    selector: 'jhi-admin',
    templateUrl: './admin.component.html',
    styleUrls: [
        'admin.css'
    ]
})
export class AdminComponent implements OnInit {

    swaggerEnabled: boolean;

    constructor(
        private router: Router,
        private profileService: ProfileService,
    ) {}

    ngOnInit() {
        this.profileService.getProfileInfo().subscribe((profileInfo) => {
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
    }
}
