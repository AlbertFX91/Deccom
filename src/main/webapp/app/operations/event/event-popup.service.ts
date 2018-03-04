import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Event } from './event.model';
import { EventService } from './event.service';

@Injectable()
export class EventPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private eventService: EventService

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
                this.eventService.findOne(id).subscribe((event) => {
                    /*
                    if (event.creationMoment) {
                        event.creationMoment = {
                            year: event.creationMoment.getFullYear(),
                            month: event.creationMoment.getMonth() + 1,
                            day: event.creationMoment.getDate()
                        };
                    }
                    if (event.startingDate) {
                        event.startingDate = {
                            year: event.startingDate.getFullYear(),
                            month: event.startingDate.getMonth() + 1,
                            day: event.startingDate.getDate()
                        };
                    }
                    if (event.endingDate) {
                        event.endingDate = {
                            year: event.endingDate.getFullYear(),
                            month: event.endingDate.getMonth() + 1,
                            day: event.endingDate.getDate()
                        };
                    }
                    */
                    this.ngbModalRef = this.eventModalRef(component, event);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.eventModalRef(component, new Event());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    eventModalRef(component: Component, event: Event): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.event = event;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }

}
