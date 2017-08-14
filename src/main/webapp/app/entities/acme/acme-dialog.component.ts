import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Acme } from './acme.model';
import { AcmePopupService } from './acme-popup.service';
import { AcmeService } from './acme.service';

@Component({
    selector: 'jhi-acme-dialog',
    templateUrl: './acme-dialog.component.html'
})
export class AcmeDialogComponent implements OnInit {

    acme: Acme;
    isSaving: boolean;
    publication_dateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private acmeService: AcmeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.acme.id !== undefined) {
            this.subscribeToSaveResponse(
                this.acmeService.update(this.acme));
        } else {
            this.subscribeToSaveResponse(
                this.acmeService.create(this.acme));
        }
    }

    private subscribeToSaveResponse(result: Observable<Acme>) {
        result.subscribe((res: Acme) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Acme) {
        this.eventManager.broadcast({ name: 'acmeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-acme-popup',
    template: ''
})
export class AcmePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private acmePopupService: AcmePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.acmePopupService
                    .open(AcmeDialogComponent as Component, params['id']);
            } else {
                this.acmePopupService
                    .open(AcmeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
