import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrSharedModule } from '../../shared';
import {
    MaterialMySuffixService,
    MaterialMySuffixPopupService,
    MaterialMySuffixComponent,
    MaterialMySuffixDetailComponent,
    MaterialMySuffixDialogComponent,
    MaterialMySuffixPopupComponent,
    MaterialMySuffixDeletePopupComponent,
    MaterialMySuffixDeleteDialogComponent,
    materialRoute,
    materialPopupRoute,
} from './';

const ENTITY_STATES = [
    ...materialRoute,
    ...materialPopupRoute,
];

@NgModule({
    imports: [
        FrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MaterialMySuffixComponent,
        MaterialMySuffixDetailComponent,
        MaterialMySuffixDialogComponent,
        MaterialMySuffixDeleteDialogComponent,
        MaterialMySuffixPopupComponent,
        MaterialMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        MaterialMySuffixComponent,
        MaterialMySuffixDialogComponent,
        MaterialMySuffixPopupComponent,
        MaterialMySuffixDeleteDialogComponent,
        MaterialMySuffixDeletePopupComponent,
    ],
    providers: [
        MaterialMySuffixService,
        MaterialMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FrMaterialMySuffixModule {}
