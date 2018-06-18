import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AnomaliaMySuffix } from './anomalia-my-suffix.model';
import { AnomaliaMySuffixService } from './anomalia-my-suffix.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-anomalia-my-suffix',
    templateUrl: './anomalia-my-suffix.component.html'
})
export class AnomaliaMySuffixComponent implements OnInit, OnDestroy {
anomalias: AnomaliaMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private anomaliaService: AnomaliaMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.anomaliaService.query().subscribe(
            (res: HttpResponse<AnomaliaMySuffix[]>) => {
                this.anomalias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAnomalias();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AnomaliaMySuffix) {
        return item.id;
    }
    registerChangeInAnomalias() {
        this.eventSubscriber = this.eventManager.subscribe('anomaliaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
