import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Precinto } from './precinto.model';
import { PrecintoPopupService } from './precinto-popup.service';
import { PrecintoService } from './precinto.service';
import { InspeccionMySuffix, InspeccionMySuffixService } from '../inspeccion-my-suffix';

@Component({
    selector: 'jhi-precinto-dialog',
    templateUrl: './precinto-dialog.component.html'
})
export class PrecintoDialogComponent implements OnInit {

    precinto: Precinto;
    isSaving: boolean;

    desde: number;
    hasta: number;

    inspeccions: InspeccionMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private precintoService: PrecintoService,
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
        if (this.precinto.id !== undefined) {
            this.subscribeToSaveResponse(
                this.precintoService.update(this.precinto));
        } else {
            this.subscribeToSaveResponse(
                this.precintoService.create(this.precinto, this.desde, this.hasta));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Precinto>>) {
        result.subscribe((res: HttpResponse<Precinto>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Precinto) {
        this.eventManager.broadcast({ name: 'precintoListModification', content: 'OK'});
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
    selector: 'jhi-precinto-popup',
    template: ''
})
export class PrecintoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private precintoPopupService: PrecintoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.precintoPopupService
                    .open(PrecintoDialogComponent as Component, params['id']);
            } else {
                this.precintoPopupService
                    .open(PrecintoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
