import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ButtonModule } from 'primeng/primeng'; 
import { DropdownModule } from 'primeng/dropdown';
import {InputSwitchModule} from 'primeng/inputswitch';
import {MultiSelectModule} from 'primeng/multiselect';
import {SelectButtonModule} from 'primeng/selectbutton';

import { FrSharedModule } from '../../shared';
import {
    InspeccionMySuffixService,
    InspeccionMySuffixPopupService,
    InspeccionMySuffixComponent,
    InspeccionMySuffixDetailComponent,
    InspeccionMySuffixDialogComponent,
    InspeccionMySuffixPopupComponent,
    InspeccionMySuffixDeletePopupComponent,
    InspeccionMySuffixDeleteDialogComponent,
    inspeccionRoute,
    inspeccionPopupRoute,
    InspeccionMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...inspeccionRoute,
    ...inspeccionPopupRoute,
];

@NgModule({
    imports: [
        FrSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        ButtonModule,    
        DropdownModule,
        InputSwitchModule,
        MultiSelectModule,
        SelectButtonModule
    ],
    declarations: [
        InspeccionMySuffixComponent,
        InspeccionMySuffixDetailComponent,
        InspeccionMySuffixDialogComponent,
        InspeccionMySuffixDeleteDialogComponent,
        InspeccionMySuffixPopupComponent,
        InspeccionMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        InspeccionMySuffixComponent,
        InspeccionMySuffixDialogComponent,
        InspeccionMySuffixPopupComponent,
        InspeccionMySuffixDeleteDialogComponent,
        InspeccionMySuffixDeletePopupComponent,
    ],
    providers: [
        InspeccionMySuffixService,
        InspeccionMySuffixPopupService,
        InspeccionMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FrInspeccionMySuffixModule {}
