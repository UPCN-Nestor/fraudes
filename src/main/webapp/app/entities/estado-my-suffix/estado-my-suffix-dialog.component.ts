import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EstadoMySuffix } from './estado-my-suffix.model';
import { EstadoMySuffixPopupService } from './estado-my-suffix-popup.service';
import { EstadoMySuffixService } from './estado-my-suffix.service';

@Component({
    selector: 'jhi-estado-my-suffix-dialog',
    templateUrl: './estado-my-suffix-dialog.component.html'
})
export class EstadoMySuffixDialogComponent implements OnInit {

    estado: EstadoMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private estadoService: EstadoMySuffixService,
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
        if (this.estado.id !== undefined) {
            this.subscribeToSaveResponse(
                this.estadoService.update(this.estado));
        } else {
            this.subscribeToSaveResponse(
                this.estadoService.create(this.estado));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EstadoMySuffix>>) {
        result.subscribe((res: HttpResponse<EstadoMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EstadoMySuffix) {
        this.eventManager.broadcast({ name: 'estadoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-estado-my-suffix-popup',
    template: ''
})
export class EstadoMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private estadoPopupService: EstadoMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.estadoPopupService
                    .open(EstadoMySuffixDialogComponent as Component, params['id']);
            } else {
                this.estadoPopupService
                    .open(EstadoMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
