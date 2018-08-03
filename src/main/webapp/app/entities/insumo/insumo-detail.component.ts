import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Insumo } from './insumo.model';
import { InsumoService } from './insumo.service';

@Component({
    selector: 'jhi-insumo-detail',
    templateUrl: './insumo-detail.component.html'
})
export class InsumoDetailComponent implements OnInit, OnDestroy {

    insumo: Insumo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private insumoService: InsumoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInsumos();
    }

    load(id) {
        this.insumoService.find(id)
            .subscribe((insumoResponse: HttpResponse<Insumo>) => {
                this.insumo = insumoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInsumos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'insumoListModification',
            (response) => this.load(this.insumo.id)
        );
    }
}
