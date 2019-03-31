import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Configslas } from 'app/shared/model/configslas.model';
import { ConfigslasService } from './configslas.service';
import { ConfigslasComponent } from './configslas.component';
import { ConfigslasDetailComponent } from './configslas-detail.component';
import { ConfigslasUpdateComponent } from './configslas-update.component';
import { ConfigslasDeletePopupComponent } from './configslas-delete-dialog.component';
import { IConfigslas } from 'app/shared/model/configslas.model';

@Injectable({ providedIn: 'root' })
export class ConfigslasResolve implements Resolve<IConfigslas> {
    constructor(private service: ConfigslasService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((configslas: HttpResponse<Configslas>) => configslas.body));
        }
        return of(new Configslas());
    }
}

export const configslasRoute: Routes = [
    {
        path: 'configslas',
        component: ConfigslasComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Configslas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'configslas/:id/view',
        component: ConfigslasDetailComponent,
        resolve: {
            configslas: ConfigslasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Configslas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'configslas/new',
        component: ConfigslasUpdateComponent,
        resolve: {
            configslas: ConfigslasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Configslas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'configslas/:id/edit',
        component: ConfigslasUpdateComponent,
        resolve: {
            configslas: ConfigslasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Configslas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const configslasPopupRoute: Routes = [
    {
        path: 'configslas/:id/delete',
        component: ConfigslasDeletePopupComponent,
        resolve: {
            configslas: ConfigslasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Configslas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
