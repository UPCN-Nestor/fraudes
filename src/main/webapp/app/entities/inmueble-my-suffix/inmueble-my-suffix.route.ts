import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { InmuebleMySuffixComponent } from './inmueble-my-suffix.component';
import { InmuebleMySuffixDetailComponent } from './inmueble-my-suffix-detail.component';
import { InmuebleMySuffixPopupComponent } from './inmueble-my-suffix-dialog.component';
import { InmuebleMySuffixDeletePopupComponent } from './inmueble-my-suffix-delete-dialog.component';

export const inmuebleRoute: Routes = [
    {
        path: 'inmueble-my-suffix',
        component: InmuebleMySuffixComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.inmueble.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'inmueble-my-suffix/:id',
        component: InmuebleMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.inmueble.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inmueblePopupRoute: Routes = [
    {
        path: 'inmueble-my-suffix-new',
        component: InmuebleMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.inmueble.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inmueble-my-suffix/:id/edit',
        component: InmuebleMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.inmueble.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inmueble-my-suffix/:id/delete',
        component: InmuebleMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.inmueble.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
