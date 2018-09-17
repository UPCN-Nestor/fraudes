import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {GraficosComponent} from './graficos/graficos.component'
import { RouterModule } from '@angular/router';
import { UserRouteAccessService } from '../shared';
import { ChartModule } from 'primeng/chart';
import { GraficosService } from './graficos/graficos.service';
import { EtapaMySuffixService } from '../entities/etapa-my-suffix/etapa-my-suffix.service';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms'; import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    ChartModule,
    FormsModule,
    ReactiveFormsModule,
    DropdownModule,
    RouterModule.forChild(
      [
        {
          path: 'graficos',
          component: GraficosComponent,
          data: {
              authorities: ['ROLE_ADMIN'],
              pageTitle: 'frApp.trabajo.home.title'
          },
          canActivate: [UserRouteAccessService]
        }
      ]
    )
  ],
  declarations: [
    GraficosComponent
  ],
  providers: [
    GraficosService
  ]
})
export class ReporteModule { }
