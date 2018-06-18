import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InmuebleMySuffix } from './inmueble-my-suffix.model';
import { InmuebleMySuffixPopupService } from './inmueble-my-suffix-popup.service';
import { InmuebleMySuffixService } from './inmueble-my-suffix.service';

@Component({
    selector: 'jhi-inmueble-my-suffix-dialog',
    templateUrl: './inmueble-my-suffix-dialog.component.html'
})
export class InmuebleMySuffixDialogComponent implements OnInit {

    inmueble: InmuebleMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private inmuebleService: InmuebleMySuffixService,
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
        if (this.inmueble.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inmuebleService.update(this.inmueble));
        } else {
            this.subscribeToSaveResponse(
                this.inmuebleService.create(this.inmueble));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<InmuebleMySuffix>>) {
        result.subscribe((res: HttpResponse<InmuebleMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: InmuebleMySuffix) {
        this.eventManager.broadcast({ name: 'inmuebleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-inmueble-my-suffix-popup',
    template: ''
})
export class InmuebleMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inmueblePopupService: InmuebleMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.inmueblePopupService
                    .open(InmuebleMySuffixDialogComponent as Component, params['id']);
            } else {
                this.inmueblePopupService
                    .open(InmuebleMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
