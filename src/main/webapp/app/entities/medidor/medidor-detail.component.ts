import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Medidor } from './medidor.model';
import { MedidorService } from './medidor.service';

@Component({
    selector: 'jhi-medidor-detail',
    templateUrl: './medidor-detail.component.html'
})
export class MedidorDetailComponent implements OnInit, OnDestroy {

    medidor: Medidor;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private medidorService: MedidorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMedidors();
    }

    load(id) {
        this.medidorService.find(id)
            .subscribe((medidorResponse: HttpResponse<Medidor>) => {
                this.medidor = medidorResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMedidors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'medidorListModification',
            (response) => this.load(this.medidor.id)
        );
    }
}
