import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TrabajoMySuffix } from './trabajo-my-suffix.model';
import { TrabajoMySuffixPopupService } from './trabajo-my-suffix-popup.service';
import { TrabajoMySuffixService } from './trabajo-my-suffix.service';
import { MaterialMySuffix, MaterialMySuffixService } from '../material-my-suffix';
import { InspeccionMySuffix, InspeccionMySuffixService } from '../inspeccion-my-suffix';

@Component({
    selector: 'jhi-trabajo-my-suffix-dialog',
    templateUrl: './trabajo-my-suffix-dialog.component.html'
})
export class TrabajoMySuffixDialogComponent implements OnInit {

    trabajo: TrabajoMySuffix;
    isSaving: boolean;

    materials: MaterialMySuffix[];

    inspeccions: InspeccionMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private trabajoService: TrabajoMySuffixService,
        private materialService: MaterialMySuffixService,
        private inspeccionService: InspeccionMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.materialService.query()
            .subscribe((res: HttpResponse<MaterialMySuffix[]>) => { this.materials = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.inspeccionService.query()
            .subscribe((res: HttpResponse<InspeccionMySuffix[]>) => { this.inspeccions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.trabajo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.trabajoService.update(this.trabajo));
        } else {
            this.subscribeToSaveResponse(
                this.trabajoService.create(this.trabajo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TrabajoMySuffix>>) {
        result.subscribe((res: HttpResponse<TrabajoMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TrabajoMySuffix) {
        this.eventManager.broadcast({ name: 'trabajoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMaterialById(index: number, item: MaterialMySuffix) {
        return item.id;
    }

    trackInspeccionById(index: number, item: InspeccionMySuffix) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-trabajo-my-suffix-popup',
    template: ''
})
export class TrabajoMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private trabajoPopupService: TrabajoMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.trabajoPopupService
                    .open(TrabajoMySuffixDialogComponent as Component, params['id']);
            } else {
                this.trabajoPopupService
                    .open(TrabajoMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
