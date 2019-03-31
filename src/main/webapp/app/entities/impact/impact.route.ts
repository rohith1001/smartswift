import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Impact } from 'app/shared/model/impact.model';
import { ImpactService } from './impact.service';
import { ImpactComponent } from './impact.component';
import { ImpactDetailComponent } from './impact-detail.component';
import { ImpactUpdateComponent } from './impact-update.component';
import { ImpactDeletePopupComponent } from './impact-delete-dialog.component';
import { IImpact } from 'app/shared/model/impact.model';

@Injectable({ providedIn: 'root' })
export class ImpactResolve implements Resolve<IImpact> {
    constructor(private service: ImpactService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((impact: HttpResponse<Impact>) => impact.body));
        }
        return of(new Impact());
    }
}

export const impactRoute: Routes = [
    {
        path: 'impact',
        component: ImpactComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Impacts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'impact/:id/view',
        component: ImpactDetailComponent,
        resolve: {
            impact: ImpactResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Impacts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'impact/new',
        component: ImpactUpdateComponent,
        resolve: {
            impact: ImpactResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Impacts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'impact/:id/edit',
        component: ImpactUpdateComponent,
        resolve: {
            impact: ImpactResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Impacts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const impactPopupRoute: Routes = [
    {
        path: 'impact/:id/delete',
        component: ImpactDeletePopupComponent,
        resolve: {
            impact: ImpactResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Impacts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
