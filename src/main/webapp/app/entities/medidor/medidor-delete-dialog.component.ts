import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Medidor } from './medidor.model';
import { MedidorPopupService } from './medidor-popup.service';
import { MedidorService } from './medidor.service';

@Component({
    selector: 'jhi-medidor-delete-dialog',
    templateUrl: './medidor-delete-dialog.component.html'
})
export class MedidorDeleteDialogComponent {

    medidor: Medidor;

    constructor(
        private medidorService: MedidorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.medidorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'medidorListModification',
                content: 'Deleted an medidor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-medidor-delete-popup',
    template: ''
})
export class MedidorDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private medidorPopupService: MedidorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.medidorPopupService
                .open(MedidorDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
