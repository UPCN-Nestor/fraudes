import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrSharedModule } from '../../shared';
import {
    MedidorService,
    MedidorPopupService,
    MedidorComponent,
    MedidorDetailComponent,
    MedidorDialogComponent,
    MedidorPopupComponent,
    MedidorDeletePopupComponent,
    MedidorDeleteDialogComponent,
    medidorRoute,
    medidorPopupRoute,
} from './';

const ENTITY_STATES = [
    ...medidorRoute,
    ...medidorPopupRoute,
];

@NgModule({
    imports: [
        FrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MedidorComponent,
        MedidorDetailComponent,
        MedidorDialogComponent,
        MedidorDeleteDialogComponent,
        MedidorPopupComponent,
        MedidorDeletePopupComponent,
    ],
    entryComponents: [
        MedidorComponent,
        MedidorDialogComponent,
        MedidorPopupComponent,
        MedidorDeleteDialogComponent,
        MedidorDeletePopupComponent,
    ],
    providers: [
        MedidorService,
        MedidorPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FrMedidorModule {}
