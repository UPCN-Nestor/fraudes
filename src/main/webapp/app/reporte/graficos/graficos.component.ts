import { Component, OnInit } from '@angular/core';
import { ChartModule } from 'primeng/chart';
import { InspeccionMySuffix } from '../../entities/inspeccion-my-suffix/inspeccion-my-suffix.model';
import { GraficosService } from './graficos.service';
import { TrabajoMySuffix } from '../../entities/trabajo-my-suffix/trabajo-my-suffix.model';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { EtapaMySuffix, EtapaMySuffixService } from '../../entities/etapa-my-suffix';
import { DropdownModule } from 'primeng/dropdown';
import { SelectItem } from 'primeng/api';
import {CalendarModule} from 'primeng/calendar';
const DEFAULT_COLORS = ['#4488EE', '#DC3912', '#FF9900', '#109618', '#990099',
'#151E8C', '#0099C6', '#DD4477', '#66AA00', '#B82E2E',
'#316395', '#994499', '#22AA99', '#AAAA11', '#6633CC',
'#E67300', '#8B0707', '#329262', '#5574A6', '#3B3EAC']

@Component({
  selector: 'jhi-graficos',
  templateUrl: './graficos.component.html',
  styles: []
})
export class GraficosComponent implements OnInit {

  constructor(private svc: GraficosService, private etapasSvc: EtapaMySuffixService) { }

  trabajos: any;
  trabajosOpt: any;
  trabajosData : Map<String, Object[]>;

  anomalias: any;
  anomaliasOpt: any;
  anomaliasData: any[];

  porfecha: any;
  porfechaOpt: any;
  porfechaData: any[];

  etapas: SelectItem[];
  etapasConTodas: SelectItem[];
  etapaSeleccionada: number = 9999;

  trabajosPorFecha: any[];
  materialesPorFecha: any[];  

  desde:Date = new Date(2018, 0, 1);
  hasta:Date = new Date(2020, 0, 1);
  es;
  colores: String[];
  
  ngOnInit() {

    let etapaSel = {}; // 'etapaId.equals': this.etapaSeleccionada

    this.es = {
        firstDayOfWeek: 0,
        dayNames: ["Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"],
        dayNamesShort: ["Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"],
        dayNamesMin: ["D","L","M","X","J","V","S"],
        monthNames: [ "Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre" ],
        monthNamesShort: [ "Ene", "Feb", "Mar", "Abr", "May", "Jun","Jul", "Ago", "Sep", "Oct", "Nov", "Dic" ],
        today: 'Hoy',
        clear: 'Limpiar'
    };

    this.colores = DEFAULT_COLORS.map(s=> s + "75");

    this.porfechaOpt = {     
        title: {
            display: false
          },
        scales: {
            xAxes: [{
                type: 'time',
                time: { 
                    round: 'day',
                    unit: 'day'
                },
                distribution: 'linear',
                ticks: {
                    source: 'auto',
          

                }
            }],
            yAxes: [{
                ticks: {
                    beginAtZero: true,
                    autoSkip: true, 
                    maxTicksLimit: 10
                }
            }]}
        };

    this.trabajosOpt = {     
        title: {
            display: false
          },
        scales: {
            xAxes: [{
                ticks: {
                    autoSkip: false
                }
            }],
            yAxes: [{
                ticks: {
                    beginAtZero: true,
                    autoSkip: true, 
                    maxTicksLimit: 10
                }
            }]}
        };
        
    this.anomaliasOpt = {     
        title: {
            display: false
            },
        scales: {
            xAxes: [{
                ticks: {
                    autoSkip: false
                }
            }],
            yAxes: [{
                ticks: {
                    beginAtZero: true,
                    autoSkip: true, 
                    maxTicksLimit: 10
                }
            }]}
        };
        
    this.etapasSvc.query().subscribe(
        (res: HttpResponse<EtapaMySuffix[]>) => { 
            
            this.etapas = res.body.map(e => { return <SelectItem>{value: e.id, label: e.descripcionCorta}; }) 
            //this.etapas = [ {value: 0, label: 'Todas las etapas'}, ...this.etapas ];   
            this.etapasConTodas = [...[{value: 9999, label: '* Todas *'}], ...this.etapas];
            this.loadTrabajosData();        
            this.loadAnomaliasData();  
            this.loadPorfechaData();        
        },
        (res: HttpErrorResponse) => { }
    );       

    this.etapaChange();

  }


  etapaChange() {    
    this.svc.byEtapaDesdeHasta(this.etapaSeleccionada, this.desde, this.hasta).subscribe(
    (res: HttpResponse<EtapaMySuffix[]>) => { 
        this.trabajosPorFecha = res.body;  
    },
    (res: HttpErrorResponse) => { });

    this.svc.materialesByEtapaDesdeHasta(this.etapaSeleccionada, this.desde, this.hasta).subscribe(
    (res: HttpResponse<EtapaMySuffix[]>) => { 
        this.materialesPorFecha = res.body;  
    },
    (res: HttpErrorResponse) => { });
  }


  loadPorfechaData() {
    this.svc.byFecha(0).subscribe(
        (res: HttpResponse<any>) => { 
            this.porfechaData = res.body;
            this.setupGraficoPorfecha();
        },
        (res: HttpErrorResponse) => { }
    );
  }

  loadTrabajosData() {
    this.svc.byTipoTrabajo(0).subscribe(
        (res: HttpResponse<any>) => { 
            this.trabajosData = res.body;
            this.setupGraficoTrabajos();
        },
        (res: HttpErrorResponse) => { }
    );
  }

  loadAnomaliasData() {
    this.svc.byAnomalia(0).subscribe(
        (res: HttpResponse<any>) => { 
            this.anomaliasData = res.body;
            this.setupGraficoAnomalias();
        },
        (res: HttpErrorResponse) => { }
    );
  }


  setupGraficoPorfecha() {
    this.porfecha = { /*
      labels: Object.keys(this.porfechaData).map(k => this.porfechaData[k]).reduce((a,b)=> [...a, ...b.map(bb=>bb[0])], []).sort()
        .filter((item, pos, ary)=> { return !pos || item != ary[pos - 1] }), */
      datasets: []
    };

    let en = 0;
    this.etapas.forEach(e=> {
        let ds = {
            label: Object.keys(this.porfechaData)[en], 
            data: Object.keys(this.porfechaData).map(k => this.porfechaData[k])[en].map(a=> {return {t: new Date(a[0]), y: a[1]}; }), 
            backgroundColor: this.colores[en],
            hoverBackgroundColor: this.colores[en]
          }
        this.porfecha.datasets.push(ds);
        en++;
    });
  }

  setupGraficoTrabajos() {
    this.trabajos = {
      labels: Object.keys(this.trabajosData).map(k => this.trabajosData[k])[0].map(a=> (a[0] + '')),
      datasets: []
    };

    let en = 0;
    Object.keys(this.trabajosData).forEach(e=> {
        let ds = {
            label: Object.keys(this.trabajosData)[en], 
            data: Object.keys(this.trabajosData).map(k => this.trabajosData[k])[en].map(a=>a[1]), 
            backgroundColor: this.colores[en],
            hoverBackgroundColor: this.colores[en]
          }
        this.trabajos.datasets.push(ds);
        en++;
    });
  }

  setupGraficoAnomalias() {
    this.anomalias = {
      labels: Object.keys(this.anomaliasData).map(k => this.anomaliasData[k])[0].map(a=> (a[0] + '')),
      datasets: []
    };

    let en = 0;
    Object.keys(this.anomaliasData).forEach(e=> {
        let ds = {
            label: Object.keys(this.anomaliasData)[en], 
            data: Object.keys(this.anomaliasData).map(k => this.anomaliasData[k])[en].map(a=>a[1]), 
            backgroundColor: this.colores[en],
            hoverBackgroundColor: this.colores[en]
          }
        this.anomalias.datasets.push(ds);
        en++;
    });
  }

 
/*
    groupBy<T>(list:T[], func:(x:T)=>string): Group<T>[] {
        let res:Group<T>[] = [];
        let group:Group<T> = null;
        list.forEach((o)=>{
            let groupName = func(o);
            if (group === null) {
                group = new Group<T>(groupName);
            }
            if (groupName != group.key) {
                res.push(group);
                group = new Group<T>(groupName);
            }
            group.members.push(o)
        });
        if (group != null) {
            res.push(group);
        }
        return res
    }*/
}

/*
class Group<T> {
    key:string;
    members:T[] = [];
    constructor(key:string) {
        this.key = key;
    }
}*/

