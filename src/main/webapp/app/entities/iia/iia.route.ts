import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Iia } from 'app/shared/model/iia.model';
import { IiaService } from './iia.service';
import { IiaComponent } from './iia.component';
import { IiaDetailComponent } from './iia-detail.component';
import { IiaUpdateComponent } from './iia-update.component';
import { IiaDeletePopupComponent } from './iia-delete-dialog.component';
import { IIia } from 'app/shared/model/iia.model';

@Injectable({ providedIn: 'root' })
export class IiaResolve implements Resolve<IIia> {
    constructor(private service: IiaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((iia: HttpResponse<Iia>) => iia.body));
        }
        return of(new Iia());
    }
}

export const iiaRoute: Routes = [
    {
        path: 'iia',
        component: IiaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Iias'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'iia/:id/view',
        component: IiaDetailComponent,
        resolve: {
            iia: IiaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Iias'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'iia/new',
        component: IiaUpdateComponent,
        resolve: {
            iia: IiaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Iias'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'iia/:id/edit',
        component: IiaUpdateComponent,
        resolve: {
            iia: IiaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Iias'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const iiaPopupRoute: Routes = [
    {
        path: 'iia/:id/delete',
        component: IiaDeletePopupComponent,
        resolve: {
            iia: IiaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Iias'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
