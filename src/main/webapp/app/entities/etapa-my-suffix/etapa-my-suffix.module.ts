import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrSharedModule } from '../../shared';
import {
    EtapaMySuffixService,
    EtapaMySuffixPopupService,
    EtapaMySuffixComponent,
    EtapaMySuffixDetailComponent,
    EtapaMySuffixDialogComponent,
    EtapaMySuffixPopupComponent,
    EtapaMySuffixDeletePopupComponent,
    EtapaMySuffixDeleteDialogComponent,
    etapaRoute,
    etapaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...etapaRoute,
    ...etapaPopupRoute,
];

@NgModule({
    imports: [
        FrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EtapaMySuffixComponent,
        EtapaMySuffixDetailComponent,
        EtapaMySuffixDialogComponent,
        EtapaMySuffixDeleteDialogComponent,
        EtapaMySuffixPopupComponent,
        EtapaMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        EtapaMySuffixComponent,
        EtapaMySuffixDialogComponent,
        EtapaMySuffixPopupComponent,
        EtapaMySuffixDeleteDialogComponent,
        EtapaMySuffixDeletePopupComponent,
    ],
    providers: [
        EtapaMySuffixService,
        EtapaMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FrEtapaMySuffixModule {}
