import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { InmuebleMySuffix } from './inmueble-my-suffix.model';
import { InmuebleMySuffixService } from './inmueble-my-suffix.service';

@Injectable()
export class InmuebleMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private inmuebleService: InmuebleMySuffixService

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
                this.inmuebleService.find(id)
                    .subscribe((inmuebleResponse: HttpResponse<InmuebleMySuffix>) => {
                        const inmueble: InmuebleMySuffix = inmuebleResponse.body;
                        this.ngbModalRef = this.inmuebleModalRef(component, inmueble);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.inmuebleModalRef(component, new InmuebleMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    inmuebleModalRef(component: Component, inmueble: InmuebleMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.inmueble = inmueble;
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
