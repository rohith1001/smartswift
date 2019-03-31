import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Issuetype } from 'app/shared/model/issuetype.model';
import { IssuetypeService } from './issuetype.service';
import { IssuetypeComponent } from './issuetype.component';
import { IssuetypeDetailComponent } from './issuetype-detail.component';
import { IssuetypeUpdateComponent } from './issuetype-update.component';
import { IssuetypeDeletePopupComponent } from './issuetype-delete-dialog.component';
import { IIssuetype } from 'app/shared/model/issuetype.model';

@Injectable({ providedIn: 'root' })
export class IssuetypeResolve implements Resolve<IIssuetype> {
    constructor(private service: IssuetypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((issuetype: HttpResponse<Issuetype>) => issuetype.body));
        }
        return of(new Issuetype());
    }
}

export const issuetypeRoute: Routes = [
    {
        path: 'issuetype',
        component: IssuetypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Issuetypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'issuetype/:id/view',
        component: IssuetypeDetailComponent,
        resolve: {
            issuetype: IssuetypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Issuetypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'issuetype/new',
        component: IssuetypeUpdateComponent,
        resolve: {
            issuetype: IssuetypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Issuetypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'issuetype/:id/edit',
        component: IssuetypeUpdateComponent,
        resolve: {
            issuetype: IssuetypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Issuetypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const issuetypePopupRoute: Routes = [
    {
        path: 'issuetype/:id/delete',
        component: IssuetypeDeletePopupComponent,
        resolve: {
            issuetype: IssuetypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Issuetypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
