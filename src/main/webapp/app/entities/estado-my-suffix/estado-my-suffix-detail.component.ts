import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EstadoMySuffix } from './estado-my-suffix.model';
import { EstadoMySuffixService } from './estado-my-suffix.service';

@Component({
    selector: 'jhi-estado-my-suffix-detail',
    templateUrl: './estado-my-suffix-detail.component.html'
})
export class EstadoMySuffixDetailComponent implements OnInit, OnDestroy {

    estado: EstadoMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private estadoService: EstadoMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEstados();
    }

    load(id) {
        this.estadoService.find(id)
            .subscribe((estadoResponse: HttpResponse<EstadoMySuffix>) => {
                this.estado = estadoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEstados() {
        this.eventSubscriber = this.eventManager.subscribe(
            'estadoListModification',
            (response) => this.load(this.estado.id)
        );
    }
}
