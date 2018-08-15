import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrSharedModule } from '../../shared';
import {
    PrecintoService,
    PrecintoPopupService,
    PrecintoComponent,
    PrecintoDetailComponent,
    PrecintoDialogComponent,
    PrecintoPopupComponent,
    PrecintoDeletePopupComponent,
    PrecintoDeleteDialogComponent,
    precintoRoute,
    precintoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...precintoRoute,
    ...precintoPopupRoute,
];

@NgModule({
    imports: [
        FrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PrecintoComponent,
        PrecintoDetailComponent,
        PrecintoDialogComponent,
        PrecintoDeleteDialogComponent,
        PrecintoPopupComponent,
        PrecintoDeletePopupComponent,
    ],
    entryComponents: [
        PrecintoComponent,
        PrecintoDialogComponent,
        PrecintoPopupComponent,
        PrecintoDeleteDialogComponent,
        PrecintoDeletePopupComponent,
    ],
    providers: [
        PrecintoService,
        PrecintoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FrPrecintoModule {}
