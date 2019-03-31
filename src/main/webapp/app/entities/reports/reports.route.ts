import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Reports } from 'app/shared/model/reports.model';
import { ReportsService } from './reports.service';
import { ReportsComponent } from './reports.component';
import { ReportsDetailComponent } from './reports-detail.component';
import { ReportsUpdateComponent } from './reports-update.component';
import { ReportsDeletePopupComponent } from './reports-delete-dialog.component';
import { IReports } from 'app/shared/model/reports.model';

@Injectable({ providedIn: 'root' })
export class ReportsResolve implements Resolve<IReports> {
    constructor(private service: ReportsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((reports: HttpResponse<Reports>) => reports.body));
        }
        return of(new Reports());
    }
}

export const reportsRoute: Routes = [
    {
        path: 'reports',
        component: ReportsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reports/:id/view',
        component: ReportsDetailComponent,
        resolve: {
            reports: ReportsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reports/new',
        component: ReportsUpdateComponent,
        resolve: {
            reports: ReportsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reports/:id/edit',
        component: ReportsUpdateComponent,
        resolve: {
            reports: ReportsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reports'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reportsPopupRoute: Routes = [
    {
        path: 'reports/:id/delete',
        component: ReportsDeletePopupComponent,
        resolve: {
            reports: ReportsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reports'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
