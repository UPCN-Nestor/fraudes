import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TipoInmuebleMySuffix } from './tipo-inmueble-my-suffix.model';
import { TipoInmuebleMySuffixService } from './tipo-inmueble-my-suffix.service';

@Component({
    selector: 'jhi-tipo-inmueble-my-suffix-detail',
    templateUrl: './tipo-inmueble-my-suffix-detail.component.html'
})
export class TipoInmuebleMySuffixDetailComponent implements OnInit, OnDestroy {

    tipoInmueble: TipoInmuebleMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tipoInmuebleService: TipoInmuebleMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTipoInmuebles();
    }

    load(id) {
        this.tipoInmuebleService.find(id)
            .subscribe((tipoInmuebleResponse: HttpResponse<TipoInmuebleMySuffix>) => {
                this.tipoInmueble = tipoInmuebleResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTipoInmuebles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tipoInmuebleListModification',
            (response) => this.load(this.tipoInmueble.id)
        );
    }
}
