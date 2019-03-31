import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Pcrelease } from 'app/shared/model/pcrelease.model';
import { PcreleaseService } from './pcrelease.service';
import { PcreleaseComponent } from './pcrelease.component';
import { PcreleaseDetailComponent } from './pcrelease-detail.component';
import { PcreleaseUpdateComponent } from './pcrelease-update.component';
import { PcreleaseDeletePopupComponent } from './pcrelease-delete-dialog.component';
import { IPcrelease } from 'app/shared/model/pcrelease.model';

@Injectable({ providedIn: 'root' })
export class PcreleaseResolve implements Resolve<IPcrelease> {
    constructor(private service: PcreleaseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((pcrelease: HttpResponse<Pcrelease>) => pcrelease.body));
        }
        return of(new Pcrelease());
    }
}

export const pcreleaseRoute: Routes = [
    {
        path: 'pcrelease',
        component: PcreleaseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pcreleases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pcrelease/:id/view',
        component: PcreleaseDetailComponent,
        resolve: {
            pcrelease: PcreleaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pcreleases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pcrelease/new',
        component: PcreleaseUpdateComponent,
        resolve: {
            pcrelease: PcreleaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pcreleases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pcrelease/:id/edit',
        component: PcreleaseUpdateComponent,
        resolve: {
            pcrelease: PcreleaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pcreleases'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pcreleasePopupRoute: Routes = [
    {
        path: 'pcrelease/:id/delete',
        component: PcreleaseDeletePopupComponent,
        resolve: {
            pcrelease: PcreleaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pcreleases'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
