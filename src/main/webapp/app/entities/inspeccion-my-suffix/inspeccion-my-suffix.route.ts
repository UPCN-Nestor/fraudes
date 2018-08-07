import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { InspeccionMySuffixComponent } from './inspeccion-my-suffix.component';
import { InspeccionMySuffixDetailComponent } from './inspeccion-my-suffix-detail.component';
import { InspeccionMySuffixPopupComponent } from './inspeccion-my-suffix-dialog.component';
import { InspeccionMySuffixDeletePopupComponent } from './inspeccion-my-suffix-delete-dialog.component';

@Injectable()
export class InspeccionMySuffixResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const inspeccionRoute: Routes = [
    {
        path: 'inspeccion-my-suffix',
        component: InspeccionMySuffixComponent,
        resolve: {
            'pagingParams': InspeccionMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'frApp.inspeccion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'inspeccion-my-suffix/:id',
        component: InspeccionMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'frApp.inspeccion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inspeccionPopupRoute: Routes = [
    {
        path: 'inspeccion-my-suffix-new',
        component: InspeccionMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'frApp.inspeccion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    
    {
        path: 'inspeccion-my-suffix/:id/edit',
        component: InspeccionMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'frApp.inspeccion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inspeccion-my-suffix/:id/delete',
        component: InspeccionMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'frApp.inspeccion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
