import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Bulkrelease } from 'app/shared/model/bulkrelease.model';
import { BulkreleaseService } from './bulkrelease.service';
import { BulkreleaseComponent } from './bulkrelease.component';
import { BulkreleaseDetailComponent } from './bulkrelease-detail.component';
import { BulkreleaseUpdateComponent } from './bulkrelease-update.component';
import { BulkreleaseDeletePopupComponent } from './bulkrelease-delete-dialog.component';
import { IBulkrelease } from 'app/shared/model/bulkrelease.model';

@Injectable({ providedIn: 'root' })
export class BulkreleaseResolve implements Resolve<IBulkrelease> {
    constructor(private service: BulkreleaseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((bulkrelease: HttpResponse<Bulkrelease>) => bulkrelease.body));
        }
        return of(new Bulkrelease());
    }
}

export const bulkreleaseRoute: Routes = [
    {
        path: 'bulkrelease',
        component: BulkreleaseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bulkreleases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bulkrelease/:id/view',
        component: BulkreleaseDetailComponent,
        resolve: {
            bulkrelease: BulkreleaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bulkreleases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bulkrelease/new',
        component: BulkreleaseUpdateComponent,
        resolve: {
            bulkrelease: BulkreleaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bulkreleases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bulkrelease/:id/edit',
        component: BulkreleaseUpdateComponent,
        resolve: {
            bulkrelease: BulkreleaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bulkreleases'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bulkreleasePopupRoute: Routes = [
    {
        path: 'bulkrelease/:id/delete',
        component: BulkreleaseDeletePopupComponent,
        resolve: {
            bulkrelease: BulkreleaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bulkreleases'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
