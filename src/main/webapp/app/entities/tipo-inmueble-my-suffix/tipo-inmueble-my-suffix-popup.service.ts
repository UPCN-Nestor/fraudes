import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { TipoInmuebleMySuffix } from './tipo-inmueble-my-suffix.model';
import { TipoInmuebleMySuffixService } from './tipo-inmueble-my-suffix.service';

@Injectable()
export class TipoInmuebleMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private tipoInmuebleService: TipoInmuebleMySuffixService

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
                this.tipoInmuebleService.find(id)
                    .subscribe((tipoInmuebleResponse: HttpResponse<TipoInmuebleMySuffix>) => {
                        const tipoInmueble: TipoInmuebleMySuffix = tipoInmuebleResponse.body;
                        this.ngbModalRef = this.tipoInmuebleModalRef(component, tipoInmueble);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.tipoInmuebleModalRef(component, new TipoInmuebleMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    tipoInmuebleModalRef(component: Component, tipoInmueble: TipoInmuebleMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tipoInmueble = tipoInmueble;
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
