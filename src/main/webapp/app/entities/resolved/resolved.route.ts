import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Resolved } from 'app/shared/model/resolved.model';
import { ResolvedService } from './resolved.service';
import { ResolvedComponent } from './resolved.component';
import { ResolvedDetailComponent } from './resolved-detail.component';
import { ResolvedUpdateComponent } from './resolved-update.component';
import { ResolvedDeletePopupComponent } from './resolved-delete-dialog.component';
import { IResolved } from 'app/shared/model/resolved.model';

@Injectable({ providedIn: 'root' })
export class ResolvedResolve implements Resolve<IResolved> {
    constructor(private service: ResolvedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((resolved: HttpResponse<Resolved>) => resolved.body));
        }
        return of(new Resolved());
    }
}

export const resolvedRoute: Routes = [
    {
        path: 'resolved',
        component: ResolvedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Resolveds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'resolved/:id/view',
        component: ResolvedDetailComponent,
        resolve: {
            resolved: ResolvedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Resolveds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'resolved/new',
        component: ResolvedUpdateComponent,
        resolve: {
            resolved: ResolvedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Resolveds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'resolved/:id/edit',
        component: ResolvedUpdateComponent,
        resolve: {
            resolved: ResolvedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Resolveds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const resolvedPopupRoute: Routes = [
    {
        path: 'resolved/:id/delete',
        component: ResolvedDeletePopupComponent,
        resolve: {
            resolved: ResolvedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Resolveds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
