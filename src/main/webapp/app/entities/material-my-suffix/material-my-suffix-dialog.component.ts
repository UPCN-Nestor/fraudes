import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MaterialMySuffix } from './material-my-suffix.model';
import { MaterialMySuffixPopupService } from './material-my-suffix-popup.service';
import { MaterialMySuffixService } from './material-my-suffix.service';
import { TrabajoMySuffix, TrabajoMySuffixService } from '../trabajo-my-suffix';

@Component({
    selector: 'jhi-material-my-suffix-dialog',
    templateUrl: './material-my-suffix-dialog.component.html'
})
export class MaterialMySuffixDialogComponent implements OnInit {

    material: MaterialMySuffix;
    isSaving: boolean;

    trabajos: TrabajoMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private materialService: MaterialMySuffixService,
        private trabajoService: TrabajoMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.trabajoService.query()
            .subscribe((res: HttpResponse<TrabajoMySuffix[]>) => { this.trabajos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.material.id !== undefined) {
            this.subscribeToSaveResponse(
                this.materialService.update(this.material));
        } else {
            this.subscribeToSaveResponse(
                this.materialService.create(this.material));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MaterialMySuffix>>) {
        result.subscribe((res: HttpResponse<MaterialMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MaterialMySuffix) {
        this.eventManager.broadcast({ name: 'materialListModification', content: 'OK'});
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
    selector: 'jhi-material-my-suffix-popup',
    template: ''
})
export class MaterialMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private materialPopupService: MaterialMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.materialPopupService
                    .open(MaterialMySuffixDialogComponent as Component, params['id']);
            } else {
                this.materialPopupService
                    .open(MaterialMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
