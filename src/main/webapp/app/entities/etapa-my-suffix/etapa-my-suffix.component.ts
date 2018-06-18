import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EtapaMySuffix } from './etapa-my-suffix.model';
import { EtapaMySuffixService } from './etapa-my-suffix.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-etapa-my-suffix',
    templateUrl: './etapa-my-suffix.component.html'
})
export class EtapaMySuffixComponent implements OnInit, OnDestroy {
etapas: EtapaMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private etapaService: EtapaMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.etapaService.query().subscribe(
            (res: HttpResponse<EtapaMySuffix[]>) => {
                this.etapas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEtapas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EtapaMySuffix) {
        return item.id;
    }
    registerChangeInEtapas() {
        this.eventSubscriber = this.eventManager.subscribe('etapaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
