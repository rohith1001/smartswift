import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Stk } from 'app/shared/model/stk.model';
import { StkService } from './stk.service';
import { StkComponent } from './stk.component';
import { StkDetailComponent } from './stk-detail.component';
import { StkUpdateComponent } from './stk-update.component';
import { StkDeletePopupComponent } from './stk-delete-dialog.component';
import { IStk } from 'app/shared/model/stk.model';

@Injectable({ providedIn: 'root' })
export class StkResolve implements Resolve<IStk> {
    constructor(private service: StkService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((stk: HttpResponse<Stk>) => stk.body));
        }
        return of(new Stk());
    }
}

export const stkRoute: Routes = [
    {
        path: 'stk',
        component: StkComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stk/:id/view',
        component: StkDetailComponent,
        resolve: {
            stk: StkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stk/new',
        component: StkUpdateComponent,
        resolve: {
            stk: StkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stk/:id/edit',
        component: StkUpdateComponent,
        resolve: {
            stk: StkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stkPopupRoute: Routes = [
    {
        path: 'stk/:id/delete',
        component: StkDeletePopupComponent,
        resolve: {
            stk: StkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
