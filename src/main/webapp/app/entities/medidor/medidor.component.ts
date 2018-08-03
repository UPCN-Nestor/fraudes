import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Medidor } from './medidor.model';
import { MedidorService } from './medidor.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-medidor',
    templateUrl: './medidor.component.html'
})
export class MedidorComponent implements OnInit, OnDestroy {
medidors: Medidor[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private medidorService: MedidorService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.medidorService.query().subscribe(
            (res: HttpResponse<Medidor[]>) => {
                this.medidors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMedidors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Medidor) {
        return item.id;
    }
    registerChangeInMedidors() {
        this.eventSubscriber = this.eventManager.subscribe('medidorListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
