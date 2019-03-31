import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { L2query } from 'app/shared/model/l-2-query.model';
import { L2queryService } from './l-2-query.service';
import { L2queryComponent } from './l-2-query.component';
import { L2queryDetailComponent } from './l-2-query-detail.component';
import { L2queryUpdateComponent } from './l-2-query-update.component';
import { L2queryDeletePopupComponent } from './l-2-query-delete-dialog.component';
import { IL2query } from 'app/shared/model/l-2-query.model';

@Injectable({ providedIn: 'root' })
export class L2queryResolve implements Resolve<IL2query> {
    constructor(private service: L2queryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((l2query: HttpResponse<L2query>) => l2query.body));
        }
        return of(new L2query());
    }
}

export const l2queryRoute: Routes = [
    {
        path: 'l-2-query',
        component: L2queryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'L2queries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'l-2-query/:id/view',
        component: L2queryDetailComponent,
        resolve: {
            l2query: L2queryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'L2queries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'l-2-query/new',
        component: L2queryUpdateComponent,
        resolve: {
            l2query: L2queryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'L2queries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'l-2-query/:id/edit',
        component: L2queryUpdateComponent,
        resolve: {
            l2query: L2queryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'L2queries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const l2queryPopupRoute: Routes = [
    {
        path: 'l-2-query/:id/delete',
        component: L2queryDeletePopupComponent,
        resolve: {
            l2query: L2queryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'L2queries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
