import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AnomaliaMySuffixComponent } from './anomalia-my-suffix.component';
import { AnomaliaMySuffixDetailComponent } from './anomalia-my-suffix-detail.component';
import { AnomaliaMySuffixPopupComponent } from './anomalia-my-suffix-dialog.component';
import { AnomaliaMySuffixDeletePopupComponent } from './anomalia-my-suffix-delete-dialog.component';

export const anomaliaRoute: Routes = [
    {
        path: 'anomalia-my-suffix',
        component: AnomaliaMySuffixComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.anomalia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'anomalia-my-suffix/:id',
        component: AnomaliaMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.anomalia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const anomaliaPopupRoute: Routes = [
    {
        path: 'anomalia-my-suffix-new',
        component: AnomaliaMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.anomalia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'anomalia-my-suffix/:id/edit',
        component: AnomaliaMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.anomalia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'anomalia-my-suffix/:id/delete',
        component: AnomaliaMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.anomalia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
