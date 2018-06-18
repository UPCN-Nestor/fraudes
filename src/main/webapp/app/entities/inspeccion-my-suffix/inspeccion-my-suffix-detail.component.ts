import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { InspeccionMySuffix } from './inspeccion-my-suffix.model';
import { InspeccionMySuffixService } from './inspeccion-my-suffix.service';

@Component({
    selector: 'jhi-inspeccion-my-suffix-detail',
    templateUrl: './inspeccion-my-suffix-detail.component.html'
})
export class InspeccionMySuffixDetailComponent implements OnInit, OnDestroy {

    inspeccion: InspeccionMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private inspeccionService: InspeccionMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInspeccions();
    }

    load(id) {
        this.inspeccionService.find(id)
            .subscribe((inspeccionResponse: HttpResponse<InspeccionMySuffix>) => {
                this.inspeccion = inspeccionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInspeccions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'inspeccionListModification',
            (response) => this.load(this.inspeccion.id)
        );
    }
}
