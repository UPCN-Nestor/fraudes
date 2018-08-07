import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EtapaMySuffixComponent } from './etapa-my-suffix.component';
import { EtapaMySuffixDetailComponent } from './etapa-my-suffix-detail.component';
import { EtapaMySuffixPopupComponent } from './etapa-my-suffix-dialog.component';
import { EtapaMySuffixDeletePopupComponent } from './etapa-my-suffix-delete-dialog.component';

export const etapaRoute: Routes = [
    {
        path: 'etapa-my-suffix',
        component: EtapaMySuffixComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.etapa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'etapa-my-suffix/:id',
        component: EtapaMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.etapa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const etapaPopupRoute: Routes = [
    {
        path: 'etapa-my-suffix-new',
        component: EtapaMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.etapa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'etapa-my-suffix/:id/edit',
        component: EtapaMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.etapa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'etapa-my-suffix/:id/delete',
        component: EtapaMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.etapa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
