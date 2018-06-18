import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EstadoMySuffix } from './estado-my-suffix.model';
import { EstadoMySuffixService } from './estado-my-suffix.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-estado-my-suffix',
    templateUrl: './estado-my-suffix.component.html'
})
export class EstadoMySuffixComponent implements OnInit, OnDestroy {
estados: EstadoMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private estadoService: EstadoMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.estadoService.query().subscribe(
            (res: HttpResponse<EstadoMySuffix[]>) => {
                this.estados = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEstados();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EstadoMySuffix) {
        return item.id;
    }
    registerChangeInEstados() {
        this.eventSubscriber = this.eventManager.subscribe('estadoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
