import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrSharedModule } from '../../shared';
import {
    EstadoMySuffixService,
    EstadoMySuffixPopupService,
    EstadoMySuffixComponent,
    EstadoMySuffixDetailComponent,
    EstadoMySuffixDialogComponent,
    EstadoMySuffixPopupComponent,
    EstadoMySuffixDeletePopupComponent,
    EstadoMySuffixDeleteDialogComponent,
    estadoRoute,
    estadoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...estadoRoute,
    ...estadoPopupRoute,
];

@NgModule({
    imports: [
        FrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EstadoMySuffixComponent,
        EstadoMySuffixDetailComponent,
        EstadoMySuffixDialogComponent,
        EstadoMySuffixDeleteDialogComponent,
        EstadoMySuffixPopupComponent,
        EstadoMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        EstadoMySuffixComponent,
        EstadoMySuffixDialogComponent,
        EstadoMySuffixPopupComponent,
        EstadoMySuffixDeleteDialogComponent,
        EstadoMySuffixDeletePopupComponent,
    ],
    providers: [
        EstadoMySuffixService,
        EstadoMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FrEstadoMySuffixModule {}
