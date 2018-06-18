import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TipoInmuebleMySuffix } from './tipo-inmueble-my-suffix.model';
import { TipoInmuebleMySuffixPopupService } from './tipo-inmueble-my-suffix-popup.service';
import { TipoInmuebleMySuffixService } from './tipo-inmueble-my-suffix.service';

@Component({
    selector: 'jhi-tipo-inmueble-my-suffix-dialog',
    templateUrl: './tipo-inmueble-my-suffix-dialog.component.html'
})
export class TipoInmuebleMySuffixDialogComponent implements OnInit {

    tipoInmueble: TipoInmuebleMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private tipoInmuebleService: TipoInmuebleMySuffixService,
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
        if (this.tipoInmueble.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tipoInmuebleService.update(this.tipoInmueble));
        } else {
            this.subscribeToSaveResponse(
                this.tipoInmuebleService.create(this.tipoInmueble));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TipoInmuebleMySuffix>>) {
        result.subscribe((res: HttpResponse<TipoInmuebleMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TipoInmuebleMySuffix) {
        this.eventManager.broadcast({ name: 'tipoInmuebleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-tipo-inmueble-my-suffix-popup',
    template: ''
})
export class TipoInmuebleMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipoInmueblePopupService: TipoInmuebleMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tipoInmueblePopupService
                    .open(TipoInmuebleMySuffixDialogComponent as Component, params['id']);
            } else {
                this.tipoInmueblePopupService
                    .open(TipoInmuebleMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
