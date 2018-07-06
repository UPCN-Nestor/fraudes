import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { InspeccionMySuffix } from './inspeccion-my-suffix.model';
import { InspeccionMySuffixService } from './inspeccion-my-suffix.service';

@Injectable()
export class InspeccionMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private inspeccionService: InspeccionMySuffixService

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
                this.inspeccionService.find(id)
                    .subscribe((inspeccionResponse: HttpResponse<InspeccionMySuffix>) => {
                        const inspeccion: InspeccionMySuffix = inspeccionResponse.body;
                        if (inspeccion.fecha) {
                            inspeccion.fecha = {
                                year: inspeccion.fecha.getFullYear(),
                                month: inspeccion.fecha.getMonth() + 1,
                                day: inspeccion.fecha.getDate()
                            };
                        }
                        inspeccion.fechayhora = this.datePipe
                            .transform(inspeccion.fechayhora, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.inspeccionModalRef(component, inspeccion);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.inspeccionModalRef(component, new InspeccionMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    inspeccionModalRef(component: Component, inspeccion: InspeccionMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.inspeccion = inspeccion;
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
