import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Acme } from './acme.model';
import { AcmeService } from './acme.service';

@Injectable()
export class AcmePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private acmeService: AcmeService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.acmeService.find(id).subscribe((acme) => {
                    if (acme.publication_date) {
                        acme.publication_date = {
                            year: acme.publication_date.getFullYear(),
                            month: acme.publication_date.getMonth() + 1,
                            day: acme.publication_date.getDate()
                        };
                    }
                    this.ngbModalRef = this.acmeModalRef(component, acme);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.acmeModalRef(component, new Acme());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    acmeModalRef(component: Component, acme: Acme): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.acme = acme;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
