import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FrInspeccionMySuffixModule } from './inspeccion-my-suffix/inspeccion-my-suffix.module';
import { FrInmuebleMySuffixModule } from './inmueble-my-suffix/inmueble-my-suffix.module';
import { FrAnomaliaMySuffixModule } from './anomalia-my-suffix/anomalia-my-suffix.module';
import { FrEstadoMySuffixModule } from './estado-my-suffix/estado-my-suffix.module';
import { FrTipoInmuebleMySuffixModule } from './tipo-inmueble-my-suffix/tipo-inmueble-my-suffix.module';
import { FrTrabajoMySuffixModule } from './trabajo-my-suffix/trabajo-my-suffix.module';
import { FrEtapaMySuffixModule } from './etapa-my-suffix/etapa-my-suffix.module';
import { FrMaterialMySuffixModule } from './material-my-suffix/material-my-suffix.module';
import { FrInsumoModule } from './insumo/insumo.module';
import { FrMedidorModule } from './medidor/medidor.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FrInspeccionMySuffixModule,
        FrInmuebleMySuffixModule,
        FrAnomaliaMySuffixModule,
        FrEstadoMySuffixModule,
        FrTipoInmuebleMySuffixModule,
        FrTrabajoMySuffixModule,
        FrEtapaMySuffixModule,
        FrMaterialMySuffixModule,
        FrInsumoModule,
        FrMedidorModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FrEntityModule {}
