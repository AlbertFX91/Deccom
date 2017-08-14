import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Acme } from './acme.model';
import { AcmePopupService } from './acme-popup.service';
import { AcmeService } from './acme.service';

@Component({
    selector: 'jhi-acme-delete-dialog',
    templateUrl: './acme-delete-dialog.component.html'
})
export class AcmeDeleteDialogComponent {

    acme: Acme;

    constructor(
        private acmeService: AcmeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.acmeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'acmeListModification',
                content: 'Deleted an acme'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-acme-delete-popup',
    template: ''
})
export class AcmeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private acmePopupService: AcmePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.acmePopupService
                .open(AcmeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
