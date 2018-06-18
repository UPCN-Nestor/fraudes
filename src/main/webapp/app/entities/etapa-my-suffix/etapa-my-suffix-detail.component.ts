import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EtapaMySuffix } from './etapa-my-suffix.model';
import { EtapaMySuffixService } from './etapa-my-suffix.service';

@Component({
    selector: 'jhi-etapa-my-suffix-detail',
    templateUrl: './etapa-my-suffix-detail.component.html'
})
export class EtapaMySuffixDetailComponent implements OnInit, OnDestroy {

    etapa: EtapaMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private etapaService: EtapaMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEtapas();
    }

    load(id) {
        this.etapaService.find(id)
            .subscribe((etapaResponse: HttpResponse<EtapaMySuffix>) => {
                this.etapa = etapaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEtapas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'etapaListModification',
            (response) => this.load(this.etapa.id)
        );
    }
}
