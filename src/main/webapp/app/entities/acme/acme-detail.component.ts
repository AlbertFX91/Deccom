import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Acme } from './acme.model';
import { AcmeService } from './acme.service';

@Component({
    selector: 'jhi-acme-detail',
    templateUrl: './acme-detail.component.html'
})
export class AcmeDetailComponent implements OnInit, OnDestroy {

    acme: Acme;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private acmeService: AcmeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAcmes();
    }

    load(id) {
        this.acmeService.find(id).subscribe((acme) => {
            this.acme = acme;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAcmes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'acmeListModification',
            (response) => this.load(this.acme.id)
        );
    }
}
