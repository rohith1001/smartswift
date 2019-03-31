import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Bulkincident } from 'app/shared/model/bulkincident.model';
import { BulkincidentService } from './bulkincident.service';
import { BulkincidentComponent } from './bulkincident.component';
import { BulkincidentDetailComponent } from './bulkincident-detail.component';
import { BulkincidentUpdateComponent } from './bulkincident-update.component';
import { BulkincidentDeletePopupComponent } from './bulkincident-delete-dialog.component';
import { IBulkincident } from 'app/shared/model/bulkincident.model';

@Injectable({ providedIn: 'root' })
export class BulkincidentResolve implements Resolve<IBulkincident> {
    constructor(private service: BulkincidentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((bulkincident: HttpResponse<Bulkincident>) => bulkincident.body));
        }
        return of(new Bulkincident());
    }
}

export const bulkincidentRoute: Routes = [
    {
        path: 'bulkincident',
        component: BulkincidentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bulkincidents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bulkincident/:id/view',
        component: BulkincidentDetailComponent,
        resolve: {
            bulkincident: BulkincidentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bulkincidents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bulkincident/new',
        component: BulkincidentUpdateComponent,
        resolve: {
            bulkincident: BulkincidentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bulkincidents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bulkincident/:id/edit',
        component: BulkincidentUpdateComponent,
        resolve: {
            bulkincident: BulkincidentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bulkincidents'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bulkincidentPopupRoute: Routes = [
    {
        path: 'bulkincident/:id/delete',
        component: BulkincidentDeletePopupComponent,
        resolve: {
            bulkincident: BulkincidentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bulkincidents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
