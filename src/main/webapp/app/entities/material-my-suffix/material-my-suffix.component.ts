import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MaterialMySuffix } from './material-my-suffix.model';
import { MaterialMySuffixService } from './material-my-suffix.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-material-my-suffix',
    templateUrl: './material-my-suffix.component.html'
})
export class MaterialMySuffixComponent implements OnInit, OnDestroy {
materials: MaterialMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private materialService: MaterialMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.materialService.query().subscribe(
            (res: HttpResponse<MaterialMySuffix[]>) => {
                this.materials = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMaterials();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MaterialMySuffix) {
        return item.id;
    }
    registerChangeInMaterials() {
        this.eventSubscriber = this.eventManager.subscribe('materialListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
