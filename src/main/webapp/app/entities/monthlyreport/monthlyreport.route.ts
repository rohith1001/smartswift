import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Monthlyreport } from 'app/shared/model/monthlyreport.model';
import { MonthlyreportService } from './monthlyreport.service';
import { MonthlyreportComponent } from './monthlyreport.component';
import { MonthlyreportDetailComponent } from './monthlyreport-detail.component';
import { MonthlyreportUpdateComponent } from './monthlyreport-update.component';
import { MonthlyreportDeletePopupComponent } from './monthlyreport-delete-dialog.component';
import { IMonthlyreport } from 'app/shared/model/monthlyreport.model';

@Injectable({ providedIn: 'root' })
export class MonthlyreportResolve implements Resolve<IMonthlyreport> {
    constructor(private service: MonthlyreportService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((monthlyreport: HttpResponse<Monthlyreport>) => monthlyreport.body));
        }
        return of(new Monthlyreport());
    }
}

export const monthlyreportRoute: Routes = [
    {
        path: 'monthlyreport',
        component: MonthlyreportComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Monthlyreports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'monthlyreport/:id/view',
        component: MonthlyreportDetailComponent,
        resolve: {
            monthlyreport: MonthlyreportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Monthlyreports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'monthlyreport/new',
        component: MonthlyreportUpdateComponent,
        resolve: {
            monthlyreport: MonthlyreportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Monthlyreports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'monthlyreport/:id/edit',
        component: MonthlyreportUpdateComponent,
        resolve: {
            monthlyreport: MonthlyreportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Monthlyreports'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const monthlyreportPopupRoute: Routes = [
    {
        path: 'monthlyreport/:id/delete',
        component: MonthlyreportDeletePopupComponent,
        resolve: {
            monthlyreport: MonthlyreportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Monthlyreports'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
