import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CV } from './cv.model';
import { CVPopupService } from './cv-popup.service';
import { CVService } from './cv.service';

@Component({
    selector: 'jhi-cv-delete-dialog',
    templateUrl: './cv-delete-dialog.component.html'
})
export class CVDeleteDialogComponent implements OnInit, OnDestroy {

    cv: CV;

    constructor(
        private cvService: CVService,
        public activeModal: NgbActiveModal,
        private cvManager: JhiEventManager
    ) {
    }

    ngOnInit() { }

    ngOnDestroy() { }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.cvService.delete(id).subscribe((response) => {
            this.cvManager.broadcast({
                name: 'cvListModification',
                content: 'Deleted a cv'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cv-delete-popup',
    template: ''
})
export class CVDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cvPopupService: CVPopupService
    ) { }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cvPopupService
                .open(CVDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
