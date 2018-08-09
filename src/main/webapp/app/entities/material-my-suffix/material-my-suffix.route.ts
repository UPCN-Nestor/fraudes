import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MaterialMySuffixComponent } from './material-my-suffix.component';
import { MaterialMySuffixDetailComponent } from './material-my-suffix-detail.component';
import { MaterialMySuffixPopupComponent } from './material-my-suffix-dialog.component';
import { MaterialMySuffixDeletePopupComponent } from './material-my-suffix-delete-dialog.component';

export const materialRoute: Routes = [
    {
        path: 'material-my-suffix',
        component: MaterialMySuffixComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.material.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'material-my-suffix/:id',
        component: MaterialMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.material.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const materialPopupRoute: Routes = [
    {
        path: 'material-my-suffix-new',
        component: MaterialMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.material.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'material-my-suffix/:id/edit',
        component: MaterialMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.material.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'material-my-suffix/:id/delete',
        component: MaterialMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'frApp.material.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
