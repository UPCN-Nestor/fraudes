import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Insumo } from './insumo.model';
import { InsumoPopupService } from './insumo-popup.service';
import { InsumoService } from './insumo.service';
import { TrabajoMySuffix, TrabajoMySuffixService } from '../trabajo-my-suffix';
import { MaterialMySuffix, MaterialMySuffixService } from '../material-my-suffix';

@Component({
    selector: 'jhi-insumo-dialog',
    templateUrl: './insumo-dialog.component.html'
})
export class InsumoDialogComponent implements OnInit {

    insumo: Insumo;
    isSaving: boolean;

    trabajos: TrabajoMySuffix[];

    materials: MaterialMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private insumoService: InsumoService,
        private trabajoService: TrabajoMySuffixService,
        private materialService: MaterialMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.trabajoService.query()
            .subscribe((res: HttpResponse<TrabajoMySuffix[]>) => { this.trabajos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.materialService.query()
            .subscribe((res: HttpResponse<MaterialMySuffix[]>) => { this.materials = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.insumo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.insumoService.update(this.insumo));
        } else {
            this.subscribeToSaveResponse(
                this.insumoService.create(this.insumo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Insumo>>) {
        result.subscribe((res: HttpResponse<Insumo>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Insumo) {
        this.eventManager.broadcast({ name: 'insumoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTrabajoById(index: number, item: TrabajoMySuffix) {
        return item.id;
    }

    trackMaterialById(index: number, item: MaterialMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-insumo-popup',
    template: ''
})
export class InsumoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private insumoPopupService: InsumoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.insumoPopupService
                    .open(InsumoDialogComponent as Component, params['id']);
            } else {
                this.insumoPopupService
                    .open(InsumoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
