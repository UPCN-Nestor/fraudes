import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TipoInmuebleMySuffixComponent } from './tipo-inmueble-my-suffix.component';
import { TipoInmuebleMySuffixDetailComponent } from './tipo-inmueble-my-suffix-detail.component';
import { TipoInmuebleMySuffixPopupComponent } from './tipo-inmueble-my-suffix-dialog.component';
import { TipoInmuebleMySuffixDeletePopupComponent } from './tipo-inmueble-my-suffix-delete-dialog.component';

export const tipoInmuebleRoute: Routes = [
    {
        path: 'tipo-inmueble-my-suffix',
        component: TipoInmuebleMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'frApp.tipoInmueble.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tipo-inmueble-my-suffix/:id',
        component: TipoInmuebleMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'frApp.tipoInmueble.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoInmueblePopupRoute: Routes = [
    {
        path: 'tipo-inmueble-my-suffix-new',
        component: TipoInmuebleMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'frApp.tipoInmueble.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tipo-inmueble-my-suffix/:id/edit',
        component: TipoInmuebleMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'frApp.tipoInmueble.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tipo-inmueble-my-suffix/:id/delete',
        component: TipoInmuebleMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'frApp.tipoInmueble.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
