import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TrabajoMySuffix } from './trabajo-my-suffix.model';
import { TrabajoMySuffixService } from './trabajo-my-suffix.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-trabajo-my-suffix',
    templateUrl: './trabajo-my-suffix.component.html'
})
export class TrabajoMySuffixComponent implements OnInit, OnDestroy {
trabajos: TrabajoMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private trabajoService: TrabajoMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.trabajoService.query().subscribe(
            (res: HttpResponse<TrabajoMySuffix[]>) => {
                this.trabajos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTrabajos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TrabajoMySuffix) {
        return item.id;
    }
    registerChangeInTrabajos() {
        this.eventSubscriber = this.eventManager.subscribe('trabajoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
