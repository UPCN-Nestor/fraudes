import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AnomaliaMySuffix } from './anomalia-my-suffix.model';
import { AnomaliaMySuffixPopupService } from './anomalia-my-suffix-popup.service';
import { AnomaliaMySuffixService } from './anomalia-my-suffix.service';

@Component({
    selector: 'jhi-anomalia-my-suffix-delete-dialog',
    templateUrl: './anomalia-my-suffix-delete-dialog.component.html'
})
export class AnomaliaMySuffixDeleteDialogComponent {

    anomalia: AnomaliaMySuffix;

    constructor(
        private anomaliaService: AnomaliaMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.anomaliaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'anomaliaListModification',
                content: 'Deleted an anomalia'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-anomalia-my-suffix-delete-popup',
    template: ''
})
export class AnomaliaMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private anomaliaPopupService: AnomaliaMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.anomaliaPopupService
                .open(AnomaliaMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
