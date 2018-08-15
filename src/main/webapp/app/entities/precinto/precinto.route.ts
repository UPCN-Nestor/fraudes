import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PrecintoComponent } from './precinto.component';
import { PrecintoDetailComponent } from './precinto-detail.component';
import { PrecintoPopupComponent } from './precinto-dialog.component';
import { PrecintoDeletePopupComponent } from './precinto-delete-dialog.component';

export const precintoRoute: Routes = [
    {
        path: 'precinto',
        component: PrecintoComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.precinto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'precinto/:id',
        component: PrecintoDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.precinto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const precintoPopupRoute: Routes = [
    {
        path: 'precinto-new',
        component: PrecintoPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.precinto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'precinto/:id/edit',
        component: PrecintoPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.precinto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'precinto/:id/delete',
        component: PrecintoDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.precinto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
