import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { InspeccionMySuffix } from './inspeccion-my-suffix.model';
import { InspeccionMySuffixService } from './inspeccion-my-suffix.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { EtapaMySuffix } from '../etapa-my-suffix';
import { EstadoMySuffix } from '../estado-my-suffix';
import { Medidor } from '../medidor';
import { DropdownModule } from 'primeng/dropdown';
import {InputSwitchModule} from 'primeng/inputswitch';
import { SelectItem } from 'primeng/api';
import { EtapaMySuffixService } from '../etapa-my-suffix/etapa-my-suffix.service';
import { EstadoMySuffixService } from '../estado-my-suffix/estado-my-suffix.service';

declare var printJS: any;

@Component({
    selector: 'jhi-inspeccion-my-suffix',
    templateUrl: './inspeccion-my-suffix.component.html'
})
export class InspeccionMySuffixComponent implements OnInit, OnDestroy {

currentAccount: any;
    inspeccions: InspeccionMySuffix[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    etapas : SelectItem[];
    etapaSeleccionada : number;

    ocultarFinalizadas : boolean = true;
    filtroMedidor : number;

    estados: EstadoMySuffix[];


    constructor(
        private inspeccionService: InspeccionMySuffixService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private etapaService: EtapaMySuffixService,
        private estadoService: EstadoMySuffixService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });

        this.etapaService.query({
            page: 1,
            size: 100,
            sort: ["id,desc"]
        }).subscribe(
            (res: HttpResponse<EtapaMySuffix[]>) => {
                this.etapas = res.body.map(x => <SelectItem>{value: x.id, label: ''+x.numero+' - '+x.descripcionCorta});
            },
            (res: HttpErrorResponse) => { 
                alert(res.message);
            }
        );

    }

    print() {
        printJS('printJS-form', 'html');
    }

    loadAll() {
        this.inspeccionService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
                (res: HttpResponse<InspeccionMySuffix[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
        );
    }



    getInspeccionesMostradas() {
        return this.inspeccions;
        /*
        //alert((<EstadoMySuffix>this.inspeccions[0].estado).descripcion);
        //return this.inspeccions.filter(x=>x.estado ? (<EstadoMySuffix>x.estado).descripcion != 'Finalizado' : true);
        let mostrar : InspeccionMySuffix[] = this.ocultarFinalizadas ? this.inspeccions.filter(x=>x.estado ? (<EstadoMySuffix>x.estado).descripcion.trim() !== 'Finalizado' : true) : this.inspeccions;
        mostrar = this.filtroMedidor && this.filtroMedidor > 0 ? 
            mostrar.filter(i => i.medidorInstalado && i.medidorInstalado.lastIndexOf("" + this.filtroMedidor)>-1) : mostrar;

        return mostrar;*/
    }

    cambioEtapa() {
        this.loadByEtapa();        
    }

    loadByEtapa() {
        let estadosMostrados = this.ocultarFinalizadas ? this.estados.filter(x=> (<EstadoMySuffix>x).descripcion.trim() !== 'Finalizado')
            .map(x=>x.id + '')
            .reduce((a,b)=>a + ', ' + b)
                : this.estados.map(x=>x.id + '').reduce((a,b)=>a + ',' + b);

        this.inspeccionService.query({
            'etapaId.equals': this.etapaSeleccionada,
            'estadoId.in': estadosMostrados,
            'medidorInstalado.contains': this.filtroMedidor ? this.filtroMedidor + '' : '',
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
                (res: HttpResponse<InspeccionMySuffix[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/inspeccion-my-suffix'], {queryParams:
            {
                'etapaId.equals': this.etapaSeleccionada,
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadByEtapa();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/inspeccion-my-suffix', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadByEtapa();
    }
    ngOnInit() {
        this.etapaSeleccionada = 1;
       
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInInspeccions();

        this.estadoService.query()
            .subscribe((res: HttpResponse<EstadoMySuffix[]>) => { 
                this.estados = res.body;     
                this.loadByEtapa();              
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: InspeccionMySuffix) {
        return item.id;
    }
    registerChangeInInspeccions() {
        this.eventSubscriber = this.eventManager.subscribe('inspeccionListModification', (response) => this.loadByEtapa());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.inspeccions = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

}
