import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { InmuebleMySuffix } from './inmueble-my-suffix.model';
import { InmuebleMySuffixService } from './inmueble-my-suffix.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-inmueble-my-suffix',
    templateUrl: './inmueble-my-suffix.component.html'
})
export class InmuebleMySuffixComponent implements OnInit, OnDestroy {
inmuebles: InmuebleMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private inmuebleService: InmuebleMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.inmuebleService.query().subscribe(
            (res: HttpResponse<InmuebleMySuffix[]>) => {
                this.inmuebles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInInmuebles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: InmuebleMySuffix) {
        return item.id;
    }
    registerChangeInInmuebles() {
        this.eventSubscriber = this.eventManager.subscribe('inmuebleListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
