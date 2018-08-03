import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MaterialMySuffix } from './material-my-suffix.model';
import { MaterialMySuffixPopupService } from './material-my-suffix-popup.service';
import { MaterialMySuffixService } from './material-my-suffix.service';

@Component({
    selector: 'jhi-material-my-suffix-dialog',
    templateUrl: './material-my-suffix-dialog.component.html'
})
export class MaterialMySuffixDialogComponent implements OnInit {

    material: MaterialMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private materialService: MaterialMySuffixService,
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
