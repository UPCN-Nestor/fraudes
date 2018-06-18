import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InmuebleMySuffix } from './inmueble-my-suffix.model';
import { InmuebleMySuffixPopupService } from './inmueble-my-suffix-popup.service';
import { InmuebleMySuffixService } from './inmueble-my-suffix.service';

@Component({
    selector: 'jhi-inmueble-my-suffix-delete-dialog',
    templateUrl: './inmueble-my-suffix-delete-dialog.component.html'
})
export class InmuebleMySuffixDeleteDialogComponent {

    inmueble: InmuebleMySuffix;

    constructor(
        private inmuebleService: InmuebleMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inmuebleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'inmuebleListModification',
                content: 'Deleted an inmueble'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-inmueble-my-suffix-delete-popup',
    template: ''
})
export class InmuebleMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inmueblePopupService: InmuebleMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.inmueblePopupService
                .open(InmuebleMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
