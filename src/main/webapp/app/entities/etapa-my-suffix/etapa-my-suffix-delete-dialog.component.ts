import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EtapaMySuffix } from './etapa-my-suffix.model';
import { EtapaMySuffixPopupService } from './etapa-my-suffix-popup.service';
import { EtapaMySuffixService } from './etapa-my-suffix.service';

@Component({
    selector: 'jhi-etapa-my-suffix-delete-dialog',
    templateUrl: './etapa-my-suffix-delete-dialog.component.html'
})
export class EtapaMySuffixDeleteDialogComponent {

    etapa: EtapaMySuffix;

    constructor(
        private etapaService: EtapaMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.etapaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'etapaListModification',
                content: 'Deleted an etapa'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-etapa-my-suffix-delete-popup',
    template: ''
})
export class EtapaMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private etapaPopupService: EtapaMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.etapaPopupService
                .open(EtapaMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
