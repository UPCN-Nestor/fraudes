import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EstadoMySuffixComponent } from './estado-my-suffix.component';
import { EstadoMySuffixDetailComponent } from './estado-my-suffix-detail.component';
import { EstadoMySuffixPopupComponent } from './estado-my-suffix-dialog.component';
import { EstadoMySuffixDeletePopupComponent } from './estado-my-suffix-delete-dialog.component';

export const estadoRoute: Routes = [
    {
        path: 'estado-my-suffix',
        component: EstadoMySuffixComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.estado.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'estado-my-suffix/:id',
        component: EstadoMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.estado.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const estadoPopupRoute: Routes = [
    {
        path: 'estado-my-suffix-new',
        component: EstadoMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.estado.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'estado-my-suffix/:id/edit',
        component: EstadoMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.estado.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'estado-my-suffix/:id/delete',
        component: EstadoMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.estado.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
