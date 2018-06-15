import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CVPopupService } from './cv-popup.service';
import { CV } from './cv.model';
import { CVService } from './cv.service';

@Component({
    selector: 'jhi-cv-dialog',
    templateUrl: './cv-dialog.component.html'
})
export class CVDialogComponent implements OnInit {

    cv: CV;
    isSaving: boolean;
    publication_dateDp: any;
    advanced: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private cvService: CVService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        const f: any = this.cv.frequency
        this.isSaving = false;
        this.advanced = isNaN(f);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cv.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cvService.update(this.cv));
        }
    }
    toggleFrequency() {
        this.advanced = !this.advanced;
    }

    private subscribeToSaveResponse(result: Observable<CV>) {
        result.subscribe((res: CV) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CV) {
        this.eventManager.broadcast({ name: 'cvListModification', content: 'OK' });
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
    selector: 'jhi-cv-popup',
    template: ''
})
export class CVPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cvPopupService: CVPopupService
    ) { }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.cvPopupService
                    .open(CVDialogComponent as Component, params['id']);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
