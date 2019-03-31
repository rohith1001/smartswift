import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Devservice } from 'app/shared/model/devservice.model';
import { DevserviceService } from './devservice.service';
import { DevserviceComponent } from './devservice.component';
import { DevserviceDetailComponent } from './devservice-detail.component';
import { DevserviceUpdateComponent } from './devservice-update.component';
import { DevserviceDeletePopupComponent } from './devservice-delete-dialog.component';
import { IDevservice } from 'app/shared/model/devservice.model';

@Injectable({ providedIn: 'root' })
export class DevserviceResolve implements Resolve<IDevservice> {
    constructor(private service: DevserviceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((devservice: HttpResponse<Devservice>) => devservice.body));
        }
        return of(new Devservice());
    }
}

export const devserviceRoute: Routes = [
    {
        path: 'devservice',
        component: DevserviceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Devservices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'devservice/:id/view',
        component: DevserviceDetailComponent,
        resolve: {
            devservice: DevserviceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Devservices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'devservice/new',
        component: DevserviceUpdateComponent,
        resolve: {
            devservice: DevserviceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Devservices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'devservice/:id/edit',
        component: DevserviceUpdateComponent,
        resolve: {
            devservice: DevserviceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Devservices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const devservicePopupRoute: Routes = [
    {
        path: 'devservice/:id/delete',
        component: DevserviceDeletePopupComponent,
        resolve: {
            devservice: DevserviceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Devservices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
