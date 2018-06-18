import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrSharedModule } from '../../shared';
import {
    InmuebleMySuffixService,
    InmuebleMySuffixPopupService,
    InmuebleMySuffixComponent,
    InmuebleMySuffixDetailComponent,
    InmuebleMySuffixDialogComponent,
    InmuebleMySuffixPopupComponent,
    InmuebleMySuffixDeletePopupComponent,
    InmuebleMySuffixDeleteDialogComponent,
    inmuebleRoute,
    inmueblePopupRoute,
} from './';

const ENTITY_STATES = [
    ...inmuebleRoute,
    ...inmueblePopupRoute,
];

@NgModule({
    imports: [
        FrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InmuebleMySuffixComponent,
        InmuebleMySuffixDetailComponent,
        InmuebleMySuffixDialogComponent,
        InmuebleMySuffixDeleteDialogComponent,
        InmuebleMySuffixPopupComponent,
        InmuebleMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        InmuebleMySuffixComponent,
        InmuebleMySuffixDialogComponent,
        InmuebleMySuffixPopupComponent,
        InmuebleMySuffixDeleteDialogComponent,
        InmuebleMySuffixDeletePopupComponent,
    ],
    providers: [
        InmuebleMySuffixService,
        InmuebleMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FrInmuebleMySuffixModule {}
