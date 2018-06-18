import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { TrabajoMySuffix } from './trabajo-my-suffix.model';
import { TrabajoMySuffixService } from './trabajo-my-suffix.service';

@Injectable()
export class TrabajoMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private trabajoService: TrabajoMySuffixService

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
                this.trabajoService.find(id)
                    .subscribe((trabajoResponse: HttpResponse<TrabajoMySuffix>) => {
                        const trabajo: TrabajoMySuffix = trabajoResponse.body;
                        this.ngbModalRef = this.trabajoModalRef(component, trabajo);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.trabajoModalRef(component, new TrabajoMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    trabajoModalRef(component: Component, trabajo: TrabajoMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.trabajo = trabajo;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
