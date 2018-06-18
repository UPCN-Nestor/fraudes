import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { MaterialMySuffix } from './material-my-suffix.model';
import { MaterialMySuffixService } from './material-my-suffix.service';

@Component({
    selector: 'jhi-material-my-suffix-detail',
    templateUrl: './material-my-suffix-detail.component.html'
})
export class MaterialMySuffixDetailComponent implements OnInit, OnDestroy {

    material: MaterialMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private materialService: MaterialMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMaterials();
    }

    load(id) {
        this.materialService.find(id)
            .subscribe((materialResponse: HttpResponse<MaterialMySuffix>) => {
                this.material = materialResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMaterials() {
        this.eventSubscriber = this.eventManager.subscribe(
            'materialListModification',
            (response) => this.load(this.material.id)
        );
    }
}
