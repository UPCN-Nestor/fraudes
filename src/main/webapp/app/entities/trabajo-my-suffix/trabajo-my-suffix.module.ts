import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrSharedModule } from '../../shared';
import {
    TrabajoMySuffixService,
    TrabajoMySuffixPopupService,
    TrabajoMySuffixComponent,
    TrabajoMySuffixDetailComponent,
    TrabajoMySuffixDialogComponent,
    TrabajoMySuffixPopupComponent,
    TrabajoMySuffixDeletePopupComponent,
    TrabajoMySuffixDeleteDialogComponent,
    trabajoRoute,
    trabajoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...trabajoRoute,
    ...trabajoPopupRoute,
];

@NgModule({
    imports: [
        FrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TrabajoMySuffixComponent,
        TrabajoMySuffixDetailComponent,
        TrabajoMySuffixDialogComponent,
        TrabajoMySuffixDeleteDialogComponent,
        TrabajoMySuffixPopupComponent,
        TrabajoMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        TrabajoMySuffixComponent,
        TrabajoMySuffixDialogComponent,
        TrabajoMySuffixPopupComponent,
        TrabajoMySuffixDeleteDialogComponent,
        TrabajoMySuffixDeletePopupComponent,
    ],
    providers: [
        TrabajoMySuffixService,
        TrabajoMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FrTrabajoMySuffixModule {}
