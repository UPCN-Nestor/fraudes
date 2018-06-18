import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrSharedModule } from '../../shared';
import {
    AnomaliaMySuffixService,
    AnomaliaMySuffixPopupService,
    AnomaliaMySuffixComponent,
    AnomaliaMySuffixDetailComponent,
    AnomaliaMySuffixDialogComponent,
    AnomaliaMySuffixPopupComponent,
    AnomaliaMySuffixDeletePopupComponent,
    AnomaliaMySuffixDeleteDialogComponent,
    anomaliaRoute,
    anomaliaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...anomaliaRoute,
    ...anomaliaPopupRoute,
];

@NgModule({
    imports: [
        FrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AnomaliaMySuffixComponent,
        AnomaliaMySuffixDetailComponent,
        AnomaliaMySuffixDialogComponent,
        AnomaliaMySuffixDeleteDialogComponent,
        AnomaliaMySuffixPopupComponent,
        AnomaliaMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        AnomaliaMySuffixComponent,
        AnomaliaMySuffixDialogComponent,
        AnomaliaMySuffixPopupComponent,
        AnomaliaMySuffixDeleteDialogComponent,
        AnomaliaMySuffixDeletePopupComponent,
    ],
    providers: [
        AnomaliaMySuffixService,
        AnomaliaMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FrAnomaliaMySuffixModule {}
