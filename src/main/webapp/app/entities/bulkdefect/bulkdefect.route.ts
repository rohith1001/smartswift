import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Bulkdefect } from 'app/shared/model/bulkdefect.model';
import { BulkdefectService } from './bulkdefect.service';
import { BulkdefectComponent } from './bulkdefect.component';
import { BulkdefectDetailComponent } from './bulkdefect-detail.component';
import { BulkdefectUpdateComponent } from './bulkdefect-update.component';
import { BulkdefectDeletePopupComponent } from './bulkdefect-delete-dialog.component';
import { IBulkdefect } from 'app/shared/model/bulkdefect.model';

@Injectable({ providedIn: 'root' })
export class BulkdefectResolve implements Resolve<IBulkdefect> {
    constructor(private service: BulkdefectService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((bulkdefect: HttpResponse<Bulkdefect>) => bulkdefect.body));
        }
        return of(new Bulkdefect());
    }
}

export const bulkdefectRoute: Routes = [
    {
        path: 'bulkdefect',
        component: BulkdefectComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bulkdefects'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bulkdefect/:id/view',
        component: BulkdefectDetailComponent,
        resolve: {
            bulkdefect: BulkdefectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bulkdefects'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bulkdefect/new',
        component: BulkdefectUpdateComponent,
        resolve: {
            bulkdefect: BulkdefectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bulkdefects'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bulkdefect/:id/edit',
        component: BulkdefectUpdateComponent,
        resolve: {
            bulkdefect: BulkdefectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bulkdefects'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bulkdefectPopupRoute: Routes = [
    {
        path: 'bulkdefect/:id/delete',
        component: BulkdefectDeletePopupComponent,
        resolve: {
            bulkdefect: BulkdefectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bulkdefects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
