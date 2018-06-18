import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MaterialMySuffix } from './material-my-suffix.model';
import { MaterialMySuffixPopupService } from './material-my-suffix-popup.service';
import { MaterialMySuffixService } from './material-my-suffix.service';

@Component({
    selector: 'jhi-material-my-suffix-delete-dialog',
    templateUrl: './material-my-suffix-delete-dialog.component.html'
})
export class MaterialMySuffixDeleteDialogComponent {

    material: MaterialMySuffix;

    constructor(
        private materialService: MaterialMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.materialService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'materialListModification',
                content: 'Deleted an material'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-material-my-suffix-delete-popup',
    template: ''
})
export class MaterialMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private materialPopupService: MaterialMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.materialPopupService
                .open(MaterialMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
