import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrSharedModule } from '../../shared';
import {
    TipoInmuebleMySuffixService,
    TipoInmuebleMySuffixPopupService,
    TipoInmuebleMySuffixComponent,
    TipoInmuebleMySuffixDetailComponent,
    TipoInmuebleMySuffixDialogComponent,
    TipoInmuebleMySuffixPopupComponent,
    TipoInmuebleMySuffixDeletePopupComponent,
    TipoInmuebleMySuffixDeleteDialogComponent,
    tipoInmuebleRoute,
    tipoInmueblePopupRoute,
} from './';

const ENTITY_STATES = [
    ...tipoInmuebleRoute,
    ...tipoInmueblePopupRoute,
];

@NgModule({
    imports: [
        FrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TipoInmuebleMySuffixComponent,
        TipoInmuebleMySuffixDetailComponent,
        TipoInmuebleMySuffixDialogComponent,
        TipoInmuebleMySuffixDeleteDialogComponent,
        TipoInmuebleMySuffixPopupComponent,
        TipoInmuebleMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        TipoInmuebleMySuffixComponent,
        TipoInmuebleMySuffixDialogComponent,
        TipoInmuebleMySuffixPopupComponent,
        TipoInmuebleMySuffixDeleteDialogComponent,
        TipoInmuebleMySuffixDeletePopupComponent,
    ],
    providers: [
        TipoInmuebleMySuffixService,
        TipoInmuebleMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FrTipoInmuebleMySuffixModule {}
