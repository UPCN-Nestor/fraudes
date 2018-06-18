import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TrabajoMySuffix } from './trabajo-my-suffix.model';
import { TrabajoMySuffixPopupService } from './trabajo-my-suffix-popup.service';
import { TrabajoMySuffixService } from './trabajo-my-suffix.service';

@Component({
    selector: 'jhi-trabajo-my-suffix-delete-dialog',
    templateUrl: './trabajo-my-suffix-delete-dialog.component.html'
})
export class TrabajoMySuffixDeleteDialogComponent {

    trabajo: TrabajoMySuffix;

    constructor(
        private trabajoService: TrabajoMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.trabajoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'trabajoListModification',
                content: 'Deleted an trabajo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-trabajo-my-suffix-delete-popup',
    template: ''
})
export class TrabajoMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private trabajoPopupService: TrabajoMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.trabajoPopupService
                .open(TrabajoMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
