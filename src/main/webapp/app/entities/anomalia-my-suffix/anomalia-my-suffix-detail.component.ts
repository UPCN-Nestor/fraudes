import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AnomaliaMySuffix } from './anomalia-my-suffix.model';
import { AnomaliaMySuffixService } from './anomalia-my-suffix.service';

@Component({
    selector: 'jhi-anomalia-my-suffix-detail',
    templateUrl: './anomalia-my-suffix-detail.component.html'
})
export class AnomaliaMySuffixDetailComponent implements OnInit, OnDestroy {

    anomalia: AnomaliaMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private anomaliaService: AnomaliaMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAnomalias();
    }

    load(id) {
        this.anomaliaService.find(id)
            .subscribe((anomaliaResponse: HttpResponse<AnomaliaMySuffix>) => {
                this.anomalia = anomaliaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAnomalias() {
        this.eventSubscriber = this.eventManager.subscribe(
            'anomaliaListModification',
            (response) => this.load(this.anomalia.id)
        );
    }
}
