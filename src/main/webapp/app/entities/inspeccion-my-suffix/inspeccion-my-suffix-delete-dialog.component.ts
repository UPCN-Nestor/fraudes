import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InspeccionMySuffix } from './inspeccion-my-suffix.model';
import { InspeccionMySuffixPopupService } from './inspeccion-my-suffix-popup.service';
import { InspeccionMySuffixService } from './inspeccion-my-suffix.service';

@Component({
    selector: 'jhi-inspeccion-my-suffix-delete-dialog',
    templateUrl: './inspeccion-my-suffix-delete-dialog.component.html'
})
export class InspeccionMySuffixDeleteDialogComponent {

    inspeccion: InspeccionMySuffix;

    constructor(
        private inspeccionService: InspeccionMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inspeccionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'inspeccionListModification',
                content: 'Deleted an inspeccion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-inspeccion-my-suffix-delete-popup',
    template: ''
})
export class InspeccionMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inspeccionPopupService: InspeccionMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.inspeccionPopupService
                .open(InspeccionMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
