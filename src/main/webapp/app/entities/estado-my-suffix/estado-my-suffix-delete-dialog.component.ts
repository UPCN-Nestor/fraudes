import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EstadoMySuffix } from './estado-my-suffix.model';
import { EstadoMySuffixPopupService } from './estado-my-suffix-popup.service';
import { EstadoMySuffixService } from './estado-my-suffix.service';

@Component({
    selector: 'jhi-estado-my-suffix-delete-dialog',
    templateUrl: './estado-my-suffix-delete-dialog.component.html'
})
export class EstadoMySuffixDeleteDialogComponent {

    estado: EstadoMySuffix;

    constructor(
        private estadoService: EstadoMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.estadoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'estadoListModification',
                content: 'Deleted an estado'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-estado-my-suffix-delete-popup',
    template: ''
})
export class EstadoMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private estadoPopupService: EstadoMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.estadoPopupService
                .open(EstadoMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
