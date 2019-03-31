import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Pcdefect } from 'app/shared/model/pcdefect.model';
import { PcdefectService } from './pcdefect.service';
import { PcdefectComponent } from './pcdefect.component';
import { PcdefectDetailComponent } from './pcdefect-detail.component';
import { PcdefectUpdateComponent } from './pcdefect-update.component';
import { PcdefectDeletePopupComponent } from './pcdefect-delete-dialog.component';
import { IPcdefect } from 'app/shared/model/pcdefect.model';

@Injectable({ providedIn: 'root' })
export class PcdefectResolve implements Resolve<IPcdefect> {
    constructor(private service: PcdefectService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((pcdefect: HttpResponse<Pcdefect>) => pcdefect.body));
        }
        return of(new Pcdefect());
    }
}

export const pcdefectRoute: Routes = [
    {
        path: 'pcdefect',
        component: PcdefectComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pcdefects'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pcdefect/:id/view',
        component: PcdefectDetailComponent,
        resolve: {
            pcdefect: PcdefectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pcdefects'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pcdefect/new',
        component: PcdefectUpdateComponent,
        resolve: {
            pcdefect: PcdefectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pcdefects'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pcdefect/:id/edit',
        component: PcdefectUpdateComponent,
        resolve: {
            pcdefect: PcdefectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pcdefects'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pcdefectPopupRoute: Routes = [
    {
        path: 'pcdefect/:id/delete',
        component: PcdefectDeletePopupComponent,
        resolve: {
            pcdefect: PcdefectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pcdefects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
