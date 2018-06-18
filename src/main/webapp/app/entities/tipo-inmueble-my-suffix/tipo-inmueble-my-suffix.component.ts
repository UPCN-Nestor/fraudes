import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TipoInmuebleMySuffix } from './tipo-inmueble-my-suffix.model';
import { TipoInmuebleMySuffixService } from './tipo-inmueble-my-suffix.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-tipo-inmueble-my-suffix',
    templateUrl: './tipo-inmueble-my-suffix.component.html'
})
export class TipoInmuebleMySuffixComponent implements OnInit, OnDestroy {
tipoInmuebles: TipoInmuebleMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tipoInmuebleService: TipoInmuebleMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.tipoInmuebleService.query().subscribe(
            (res: HttpResponse<TipoInmuebleMySuffix[]>) => {
                this.tipoInmuebles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTipoInmuebles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TipoInmuebleMySuffix) {
        return item.id;
    }
    registerChangeInTipoInmuebles() {
        this.eventSubscriber = this.eventManager.subscribe('tipoInmuebleListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
