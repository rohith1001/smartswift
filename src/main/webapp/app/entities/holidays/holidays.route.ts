import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Holidays } from 'app/shared/model/holidays.model';
import { HolidaysService } from './holidays.service';
import { HolidaysComponent } from './holidays.component';
import { HolidaysDetailComponent } from './holidays-detail.component';
import { HolidaysUpdateComponent } from './holidays-update.component';
import { HolidaysDeletePopupComponent } from './holidays-delete-dialog.component';
import { IHolidays } from 'app/shared/model/holidays.model';

@Injectable({ providedIn: 'root' })
export class HolidaysResolve implements Resolve<IHolidays> {
    constructor(private service: HolidaysService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((holidays: HttpResponse<Holidays>) => holidays.body));
        }
        return of(new Holidays());
    }
}

export const holidaysRoute: Routes = [
    {
        path: 'holidays',
        component: HolidaysComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'holidays/:id/view',
        component: HolidaysDetailComponent,
        resolve: {
            holidays: HolidaysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'holidays/new',
        component: HolidaysUpdateComponent,
        resolve: {
            holidays: HolidaysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'holidays/:id/edit',
        component: HolidaysUpdateComponent,
        resolve: {
            holidays: HolidaysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const holidaysPopupRoute: Routes = [
    {
        path: 'holidays/:id/delete',
        component: HolidaysDeletePopupComponent,
        resolve: {
            holidays: HolidaysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
