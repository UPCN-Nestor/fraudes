import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Insumo } from './insumo.model';
import { InsumoService } from './insumo.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-insumo',
    templateUrl: './insumo.component.html'
})
export class InsumoComponent implements OnInit, OnDestroy {
insumos: Insumo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private insumoService: InsumoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.insumoService.query().subscribe(
            (res: HttpResponse<Insumo[]>) => {
                this.insumos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInInsumos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Insumo) {
        return item.id;
    }
    registerChangeInInsumos() {
        this.eventSubscriber = this.eventManager.subscribe('insumoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
