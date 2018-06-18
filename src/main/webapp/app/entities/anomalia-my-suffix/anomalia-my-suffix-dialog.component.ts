import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AnomaliaMySuffix } from './anomalia-my-suffix.model';
import { AnomaliaMySuffixPopupService } from './anomalia-my-suffix-popup.service';
import { AnomaliaMySuffixService } from './anomalia-my-suffix.service';
import { InspeccionMySuffix, InspeccionMySuffixService } from '../inspeccion-my-suffix';

@Component({
    selector: 'jhi-anomalia-my-suffix-dialog',
    templateUrl: './anomalia-my-suffix-dialog.component.html'
})
export class AnomaliaMySuffixDialogComponent implements OnInit {

    anomalia: AnomaliaMySuffix;
    isSaving: boolean;

    inspeccions: InspeccionMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private anomaliaService: AnomaliaMySuffixService,
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
        if (this.anomalia.id !== undefined) {
            this.subscribeToSaveResponse(
                this.anomaliaService.update(this.anomalia));
        } else {
            this.subscribeToSaveResponse(
                this.anomaliaService.create(this.anomalia));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AnomaliaMySuffix>>) {
        result.subscribe((res: HttpResponse<AnomaliaMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AnomaliaMySuffix) {
        this.eventManager.broadcast({ name: 'anomaliaListModification', content: 'OK'});
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
    selector: 'jhi-anomalia-my-suffix-popup',
    template: ''
})
export class AnomaliaMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private anomaliaPopupService: AnomaliaMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.anomaliaPopupService
                    .open(AnomaliaMySuffixDialogComponent as Component, params['id']);
            } else {
                this.anomaliaPopupService
                    .open(AnomaliaMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
