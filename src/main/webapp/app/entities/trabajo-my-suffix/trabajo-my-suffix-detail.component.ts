import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TrabajoMySuffix } from './trabajo-my-suffix.model';
import { TrabajoMySuffixService } from './trabajo-my-suffix.service';

@Component({
    selector: 'jhi-trabajo-my-suffix-detail',
    templateUrl: './trabajo-my-suffix-detail.component.html'
})
export class TrabajoMySuffixDetailComponent implements OnInit, OnDestroy {

    trabajo: TrabajoMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private trabajoService: TrabajoMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTrabajos();
    }

    load(id) {
        this.trabajoService.find(id)
            .subscribe((trabajoResponse: HttpResponse<TrabajoMySuffix>) => {
                this.trabajo = trabajoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTrabajos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'trabajoListModification',
            (response) => this.load(this.trabajo.id)
        );
    }
}
