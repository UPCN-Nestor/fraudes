import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
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
import { Precinto, PrecintoService } from '../precinto';

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

    precintos: Precinto[];

	currentAccount: any;

    fechaTomaDp: any;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private inspeccionService: InspeccionMySuffixService,
        private precintoService: PrecintoService,
        private anomaliaService: AnomaliaMySuffixService,
        private trabajoService: TrabajoMySuffixService,
        private inmuebleService: InmuebleMySuffixService,
        private etapaService: EtapaMySuffixService,
        private estadoService: EstadoMySuffixService,
        private tipoInmuebleService: TipoInmuebleMySuffixService,
        private medidorService: MedidorService,
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private principal: Principal,
        private elementRef: ElementRef
    ) {
    }
 
    isMobile() { 
        if(navigator.userAgent.match(/Android/i))
            return true;
        else return false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.inspeccion, this.elementRef, field, fieldContentType, idInput);
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
        this.trabajoService.query({sort: ['descripcion']})
            .subscribe((res: HttpResponse<TrabajoMySuffix[]>) => { 
                this.trabajos = res.body.sort((a,b)=>{return a.descripcion < b.descripcion ? -1 : 1});
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.inmuebleService.query()
            .subscribe((res: HttpResponse<InmuebleMySuffix[]>) => { this.inmuebles = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.precintoService.query()
            .subscribe((res: HttpResponse<Precinto[]>) => { this.precintos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.etapaService.query()
            .subscribe((res: HttpResponse<EtapaMySuffix[]>) => { 
                this.etapas = res.body; 
                if(!this.inspeccion.id) {

                    this.inspeccion.etapa = <EtapaMySuffix>(this.etapas.filter(x=>x.numero==this.inspeccion["etapa_activa_id"])[0]);
                    //alert(this.etapas.filter(x=>x.numero==0).numero);
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.estadoService.query()
            .subscribe((res: HttpResponse<EstadoMySuffix[]>) => { 
                this.estados = res.body; 
              
                // Las inspecciones ordenadas por el admin defaultean a estado Inicial, las editadas por los demás, a Finalizado
                this.principal.hasAnyAuthority(['ROLE_ADMIN']).then(v => {
                    if(v==true)
                        this.inspeccion.estado = this.estados.filter(e=>e.descripcion.trim()=="Inicial")[0];
                    else {
                        this.inspeccion.estado = this.estados.filter(e=>e.descripcion.trim()=="Finalizado")[0];
                    }
                });                  
            }, (res: HttpErrorResponse) => this.onError(res.message));
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

        //this.inspeccion.fechaToma = this.inspeccion.fechaToma.year + "-" + this.inspeccion.fechaToma.month + "-" + this.inspeccion.fechaToma.day;
        //alert(Object.keys(this.inspeccion.fechaToma));
    }

    getAnomalias() : SelectItem[] {
        return this.anomalias ? this.anomalias.map(x=><SelectItem>{label:x.descripcion, value:x}) : [];
    }

    getNombre() : string {
        if(!this.inspeccion.etapa || !this.etapas)
            return;

        if(this.inspeccion.etapa.id==0)
            return 'Excepcional';
        else
            return '' + this.etapas.filter(e=> e.id == this.inspeccion.etapa.id)[0].numero + '/' + this.inspeccion.orden;
    }

    usaCable() : boolean {
        let usa = false;
        if(this.trabajos && this.inspeccion.trabajos && this.inspeccion.trabajos.filter(x=>this.trabajos.find(t=>t.id==x.id && t.usaCable)).length > 0)
            usa = true;

        if(usa)
            this.inspeccion.mtsCable = 7;
        else
            this.inspeccion.mtsCable = 0;
            
        return usa;
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

    trackPrecintoById(index: number, item: Precinto) {
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
                    .open(InspeccionMySuffixDialogComponent as Component, params['id'], 0);
            } else {
                this.inspeccionPopupService
                    .open(InspeccionMySuffixDialogComponent as Component, 0, params['etapa']);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
