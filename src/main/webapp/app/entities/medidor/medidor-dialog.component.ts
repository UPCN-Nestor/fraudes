import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Medidor } from './medidor.model';
import { MedidorPopupService } from './medidor-popup.service';
import { MedidorService } from './medidor.service';
import { InspeccionMySuffix, InspeccionMySuffixService } from '../inspeccion-my-suffix';

@Component({
    selector: 'jhi-medidor-dialog',
    templateUrl: './medidor-dialog.component.html'
})
export class MedidorDialogComponent implements OnInit {

    medidor: Medidor;
    isSaving: boolean;

    inspeccions: InspeccionMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private medidorService: MedidorService,
        private inspeccionService: InspeccionMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.inspeccionService.query()
            .subscribe((res: HttpResponse<InspeccionMySuffix[]>) => { this.inspeccions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.medidor.id !== undefined) {
            this.subscribeToSaveResponse(
                this.medidorService.update(this.medidor));
        } else {
            this.subscribeToSaveResponse(
                this.medidorService.create(this.medidor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Medidor>>) {
        result.subscribe((res: HttpResponse<Medidor>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Medidor) {
        this.eventManager.broadcast({ name: 'medidorListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackInspeccionById(index: number, item: InspeccionMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-medidor-popup',
    template: ''
})
export class MedidorPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private medidorPopupService: MedidorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.medidorPopupService
                    .open(MedidorDialogComponent as Component, params['id']);
            } else {
                this.medidorPopupService
                    .open(MedidorDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
