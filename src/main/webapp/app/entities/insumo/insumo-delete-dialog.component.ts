import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Insumo } from './insumo.model';
import { InsumoPopupService } from './insumo-popup.service';
import { InsumoService } from './insumo.service';

@Component({
    selector: 'jhi-insumo-delete-dialog',
    templateUrl: './insumo-delete-dialog.component.html'
})
export class InsumoDeleteDialogComponent {

    insumo: Insumo;

    constructor(
        private insumoService: InsumoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.insumoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'insumoListModification',
                content: 'Deleted an insumo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-insumo-delete-popup',
    template: ''
})
export class InsumoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private insumoPopupService: InsumoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.insumoPopupService
                .open(InsumoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
