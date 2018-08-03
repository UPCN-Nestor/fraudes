import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrSharedModule } from '../../shared';
import {
    InsumoService,
    InsumoPopupService,
    InsumoComponent,
    InsumoDetailComponent,
    InsumoDialogComponent,
    InsumoPopupComponent,
    InsumoDeletePopupComponent,
    InsumoDeleteDialogComponent,
    insumoRoute,
    insumoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...insumoRoute,
    ...insumoPopupRoute,
];

@NgModule({
    imports: [
        FrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InsumoComponent,
        InsumoDetailComponent,
        InsumoDialogComponent,
        InsumoDeleteDialogComponent,
        InsumoPopupComponent,
        InsumoDeletePopupComponent,
    ],
    entryComponents: [
        InsumoComponent,
        InsumoDialogComponent,
        InsumoPopupComponent,
        InsumoDeleteDialogComponent,
        InsumoDeletePopupComponent,
    ],
    providers: [
        InsumoService,
        InsumoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FrInsumoModule {}
