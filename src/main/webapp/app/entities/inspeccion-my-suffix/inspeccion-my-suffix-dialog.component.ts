import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { InspeccionMySuffix } from './inspeccion-my-suffix.model';
import { InspeccionMySuffixPopupService } from './inspeccion-my-suffix-popup.service';
import { InspeccionMySuffixService } from './inspeccion-my-suffix.service';
import { AnomaliaMySuffix, AnomaliaMySuffixService } from '../anomalia-my-suffix';
import { TrabajoMySuffix, TrabajoMySuffixService } from '../trabajo-my-suffix';
import { InmuebleMySuffix, InmuebleMySuffixService } from '../inmueble-my-suffix';
import { EtapaMySuffix, EtapaMySuffixService } from '../etapa-my-suffix';
import { EstadoMySuffix, EstadoMySuffixService } from '../estado-my-suffix';
import { TipoInmuebleMySuffix, TipoInmuebleMySuffixService } from '../tipo-inmueble-my-suffix';
import { Medidor, MedidorService } from '../medidor';

import { Principal } from '../../shared';

import {MultiSelectModule} from 'primeng/multiselect';
import { SelectItem } from 'primeng/api';
import {SelectButtonModule} from 'primeng/selectbutton';

@Component({
    selector: 'jhi-inspeccion-my-suffix-dialog',
    templateUrl: './inspeccion-my-suffix-dialog.component.html'
})
export class InspeccionMySuffixDialogComponent implements OnInit {

    inspeccion: InspeccionMySuffix;
    isSaving: boolean;

    anomalias: AnomaliaMySuffix[];

    trabajos: TrabajoMySuffix[];

    inmuebles: InmuebleMySuffix[];

    etapas: EtapaMySuffix[];

    estados: EstadoMySuffix[];

    tipoinmuebles: TipoInmuebleMySuffix[];

    medidornuevos: Medidor[];

	currentAccount: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private inspeccionService: InspeccionMySuffixService,
        private anomaliaService: AnomaliaMySuffixService,
        private trabajoService: TrabajoMySuffixService,
        private inmuebleService: InmuebleMySuffixService,
        private etapaService: EtapaMySuffixService,
        private estadoService: EstadoMySuffixService,
        private tipoInmuebleService: TipoInmuebleMySuffixService,
        private medidorService: MedidorService,
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
		private principal: Principal
    ) {
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    loadFoto(e) {
        alert(e.target.data);
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.inspeccion.usuario = this.currentAccount.login;
        });

        //this.pruebas = [{label:'x', value:'xx'}, {label:'y', value: 'yy'}];
        //this.prueba = ['xx'];

        this.isSaving = false;
        this.anomaliaService.query()
            .subscribe((res: HttpResponse<AnomaliaMySuffix[]>) => { this.anomalias = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.trabajoService.query()
            .subscribe((res: HttpResponse<TrabajoMySuffix[]>) => { 
                this.trabajos = res.body;
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.inmuebleService.query()
            .subscribe((res: HttpResponse<InmuebleMySuffix[]>) => { this.inmuebles = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.etapaService.query()
            .subscribe((res: HttpResponse<EtapaMySuffix[]>) => { 
                this.etapas = res.body; 
                if(!this.inspeccion.id) {

                    this.inspeccion.etapa = <EtapaMySuffix>(this.etapas.filter(x=>x.numero==0)[0]);
                    //alert(this.etapas.filter(x=>x.numero==0).numero);
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.estadoService.query()
            .subscribe((res: HttpResponse<EstadoMySuffix[]>) => { this.estados = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tipoInmuebleService.query()
            .subscribe((res: HttpResponse<TipoInmuebleMySuffix[]>) => { this.tipoinmuebles = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.medidorService
            .query({filter: 'inspeccion-is-null'})
            .subscribe((res: HttpResponse<Medidor[]>) => {
                if (!this.inspeccion.medidorNuevo || !this.inspeccion.medidorNuevo.id) {
                    this.medidornuevos = res.body;
                } else {
                    this.medidorService
                        .find(this.inspeccion.medidorNuevo.id)
                        .subscribe((subRes: HttpResponse<Medidor>) => {
                            this.medidornuevos = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));

	    var d = new Date(); 
        d.setTime(d.getTime() - (3*60*60*1000)); 
        var dd = d.toISOString();
        this.inspeccion.fechahora = dd.substring(0,dd.length-5);
    }

    getAnomalias() : SelectItem[] {
        return this.anomalias ? this.anomalias.map(x=><SelectItem>{label:x.descripcion, value:x}) : [];
    }

    getNombre() : string {
        if(this.inspeccion.etapa.id==0)
            return 'Excepcional';
        else
            return '' + this.etapas.filter(e=> e.id == this.inspeccion.etapa.id)[0].numero + '/' + this.inspeccion.orden;
    }

    usaMedidor() : boolean {
        if(this.inspeccion.trabajos==null) 
            return false;
        return this.inspeccion.trabajos.filter(x=>this.trabajos.find(t=>t.id==x.id).usaMedidor==true).length > 0;
    }


    str(obj) {
        return JSON.stringify(obj);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.inspeccion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inspeccionService.update(this.inspeccion));
        } else {
            this.subscribeToSaveResponse(
                this.inspeccionService.create(this.inspeccion));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<InspeccionMySuffix>>) {
        result.subscribe((res: HttpResponse<InspeccionMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: InspeccionMySuffix) {
        this.eventManager.broadcast({ name: 'inspeccionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAnomaliaById(index: number, item: AnomaliaMySuffix) {
        return item.id;
    }

    trackTrabajoById(index: number, item: TrabajoMySuffix) {
        return item.id;
    }

    trackInmuebleById(index: number, item: InmuebleMySuffix) {
        return item.id;
    }

    trackEtapaById(index: number, item: EtapaMySuffix) {
        return item.id;
    }

    trackEstadoById(index: number, item: EstadoMySuffix) {
        return item.id;
    }

    trackTipoInmuebleById(index: number, item: TipoInmuebleMySuffix) {
        return item.id;
    }

    trackMedidorById(index: number, item: Medidor) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-inspeccion-my-suffix-popup',
    template: ''
})
export class InspeccionMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inspeccionPopupService: InspeccionMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.inspeccionPopupService
                    .open(InspeccionMySuffixDialogComponent as Component, params['id']);
            } else {
                this.inspeccionPopupService
                    .open(InspeccionMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
