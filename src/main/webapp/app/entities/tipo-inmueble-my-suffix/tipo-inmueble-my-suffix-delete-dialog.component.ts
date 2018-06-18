import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TipoInmuebleMySuffix } from './tipo-inmueble-my-suffix.model';
import { TipoInmuebleMySuffixPopupService } from './tipo-inmueble-my-suffix-popup.service';
import { TipoInmuebleMySuffixService } from './tipo-inmueble-my-suffix.service';

@Component({
    selector: 'jhi-tipo-inmueble-my-suffix-delete-dialog',
    templateUrl: './tipo-inmueble-my-suffix-delete-dialog.component.html'
})
export class TipoInmuebleMySuffixDeleteDialogComponent {

    tipoInmueble: TipoInmuebleMySuffix;

    constructor(
        private tipoInmuebleService: TipoInmuebleMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoInmuebleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tipoInmuebleListModification',
                content: 'Deleted an tipoInmueble'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-inmueble-my-suffix-delete-popup',
    template: ''
})
export class TipoInmuebleMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipoInmueblePopupService: TipoInmuebleMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tipoInmueblePopupService
                .open(TipoInmuebleMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
