import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Precinto } from './precinto.model';
import { PrecintoPopupService } from './precinto-popup.service';
import { PrecintoService } from './precinto.service';

@Component({
    selector: 'jhi-precinto-delete-dialog',
    templateUrl: './precinto-delete-dialog.component.html'
})
export class PrecintoDeleteDialogComponent {

    precinto: Precinto;

    constructor(
        private precintoService: PrecintoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.precintoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'precintoListModification',
                content: 'Deleted an precinto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-precinto-delete-popup',
    template: ''
})
export class PrecintoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private precintoPopupService: PrecintoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.precintoPopupService
                .open(PrecintoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
