import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { InmuebleMySuffix } from './inmueble-my-suffix.model';
import { InmuebleMySuffixService } from './inmueble-my-suffix.service';

@Component({
    selector: 'jhi-inmueble-my-suffix-detail',
    templateUrl: './inmueble-my-suffix-detail.component.html'
})
export class InmuebleMySuffixDetailComponent implements OnInit, OnDestroy {

    inmueble: InmuebleMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private inmuebleService: InmuebleMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInmuebles();
    }

    load(id) {
        this.inmuebleService.find(id)
            .subscribe((inmuebleResponse: HttpResponse<InmuebleMySuffix>) => {
                this.inmueble = inmuebleResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInmuebles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'inmuebleListModification',
            (response) => this.load(this.inmueble.id)
        );
    }
}
