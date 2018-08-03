import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { InsumoComponent } from './insumo.component';
import { InsumoDetailComponent } from './insumo-detail.component';
import { InsumoPopupComponent } from './insumo-dialog.component';
import { InsumoDeletePopupComponent } from './insumo-delete-dialog.component';

export const insumoRoute: Routes = [
    {
        path: 'insumo',
        component: InsumoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'frApp.insumo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'insumo/:id',
        component: InsumoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'frApp.insumo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const insumoPopupRoute: Routes = [
    {
        path: 'insumo-new',
        component: InsumoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'frApp.insumo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'insumo/:id/edit',
        component: InsumoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'frApp.insumo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'insumo/:id/delete',
        component: InsumoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'frApp.insumo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
