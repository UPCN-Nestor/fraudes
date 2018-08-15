import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TrabajoMySuffixComponent } from './trabajo-my-suffix.component';
import { TrabajoMySuffixDetailComponent } from './trabajo-my-suffix-detail.component';
import { TrabajoMySuffixPopupComponent } from './trabajo-my-suffix-dialog.component';
import { TrabajoMySuffixDeletePopupComponent } from './trabajo-my-suffix-delete-dialog.component';

export const trabajoRoute: Routes = [
    {
        path: 'trabajo-my-suffix',
        component: TrabajoMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'frApp.trabajo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'trabajo-my-suffix/:id',
        component: TrabajoMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'frApp.trabajo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const trabajoPopupRoute: Routes = [
    {
        path: 'trabajo-my-suffix-new',
        component: TrabajoMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'frApp.trabajo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'trabajo-my-suffix/:id/edit',
        component: TrabajoMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'frApp.trabajo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'trabajo-my-suffix/:id/delete',
        component: TrabajoMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'frApp.trabajo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
