import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Precinto } from './precinto.model';
import { PrecintoService } from './precinto.service';

@Component({
    selector: 'jhi-precinto-detail',
    templateUrl: './precinto-detail.component.html'
})
export class PrecintoDetailComponent implements OnInit, OnDestroy {

    precinto: Precinto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private precintoService: PrecintoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPrecintos();
    }

    load(id) {
        this.precintoService.find(id)
            .subscribe((precintoResponse: HttpResponse<Precinto>) => {
                this.precinto = precintoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPrecintos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'precintoListModification',
            (response) => this.load(this.precinto.id)
        );
    }
}
