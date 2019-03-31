import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Configtype } from 'app/shared/model/configtype.model';
import { ConfigtypeService } from './configtype.service';
import { ConfigtypeComponent } from './configtype.component';
import { ConfigtypeDetailComponent } from './configtype-detail.component';
import { ConfigtypeUpdateComponent } from './configtype-update.component';
import { ConfigtypeDeletePopupComponent } from './configtype-delete-dialog.component';
import { IConfigtype } from 'app/shared/model/configtype.model';

@Injectable({ providedIn: 'root' })
export class ConfigtypeResolve implements Resolve<IConfigtype> {
    constructor(private service: ConfigtypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((configtype: HttpResponse<Configtype>) => configtype.body));
        }
        return of(new Configtype());
    }
}

export const configtypeRoute: Routes = [
    {
        path: 'configtype',
        component: ConfigtypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Configtypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'configtype/:id/view',
        component: ConfigtypeDetailComponent,
        resolve: {
            configtype: ConfigtypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Configtypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'configtype/new',
        component: ConfigtypeUpdateComponent,
        resolve: {
            configtype: ConfigtypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Configtypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'configtype/:id/edit',
        component: ConfigtypeUpdateComponent,
        resolve: {
            configtype: ConfigtypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Configtypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const configtypePopupRoute: Routes = [
    {
        path: 'configtype/:id/delete',
        component: ConfigtypeDeletePopupComponent,
        resolve: {
            configtype: ConfigtypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Configtypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
