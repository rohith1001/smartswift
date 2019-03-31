import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Incidenttype } from 'app/shared/model/incidenttype.model';
import { IncidenttypeService } from './incidenttype.service';
import { IncidenttypeComponent } from './incidenttype.component';
import { IncidenttypeDetailComponent } from './incidenttype-detail.component';
import { IncidenttypeUpdateComponent } from './incidenttype-update.component';
import { IncidenttypeDeletePopupComponent } from './incidenttype-delete-dialog.component';
import { IIncidenttype } from 'app/shared/model/incidenttype.model';

@Injectable({ providedIn: 'root' })
export class IncidenttypeResolve implements Resolve<IIncidenttype> {
    constructor(private service: IncidenttypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((incidenttype: HttpResponse<Incidenttype>) => incidenttype.body));
        }
        return of(new Incidenttype());
    }
}

export const incidenttypeRoute: Routes = [
    {
        path: 'incidenttype',
        component: IncidenttypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Incidenttypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'incidenttype/:id/view',
        component: IncidenttypeDetailComponent,
        resolve: {
            incidenttype: IncidenttypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Incidenttypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'incidenttype/new',
        component: IncidenttypeUpdateComponent,
        resolve: {
            incidenttype: IncidenttypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Incidenttypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'incidenttype/:id/edit',
        component: IncidenttypeUpdateComponent,
        resolve: {
            incidenttype: IncidenttypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Incidenttypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const incidenttypePopupRoute: Routes = [
    {
        path: 'incidenttype/:id/delete',
        component: IncidenttypeDeletePopupComponent,
        resolve: {
            incidenttype: IncidenttypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Incidenttypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
