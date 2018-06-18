import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { AnomaliaMySuffix } from './anomalia-my-suffix.model';
import { AnomaliaMySuffixService } from './anomalia-my-suffix.service';

@Injectable()
export class AnomaliaMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private anomaliaService: AnomaliaMySuffixService

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
                this.anomaliaService.find(id)
                    .subscribe((anomaliaResponse: HttpResponse<AnomaliaMySuffix>) => {
                        const anomalia: AnomaliaMySuffix = anomaliaResponse.body;
                        this.ngbModalRef = this.anomaliaModalRef(component, anomalia);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.anomaliaModalRef(component, new AnomaliaMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    anomaliaModalRef(component: Component, anomalia: AnomaliaMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.anomalia = anomalia;
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
