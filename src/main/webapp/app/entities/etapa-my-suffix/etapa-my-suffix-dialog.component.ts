import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EtapaMySuffix } from './etapa-my-suffix.model';
import { EtapaMySuffixPopupService } from './etapa-my-suffix-popup.service';
import { EtapaMySuffixService } from './etapa-my-suffix.service';

@Component({
    selector: 'jhi-etapa-my-suffix-dialog',
    templateUrl: './etapa-my-suffix-dialog.component.html'
})
export class EtapaMySuffixDialogComponent implements OnInit {

    etapa: EtapaMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private etapaService: EtapaMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.etapa.id !== undefined) {
            this.subscribeToSaveResponse(
                this.etapaService.update(this.etapa));
        } else {
            this.subscribeToSaveResponse(
                this.etapaService.create(this.etapa));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EtapaMySuffix>>) {
        result.subscribe((res: HttpResponse<EtapaMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EtapaMySuffix) {
        this.eventManager.broadcast({ name: 'etapaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-etapa-my-suffix-popup',
    template: ''
})
export class EtapaMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private etapaPopupService: EtapaMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.etapaPopupService
                    .open(EtapaMySuffixDialogComponent as Component, params['id']);
            } else {
                this.etapaPopupService
                    .open(EtapaMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
