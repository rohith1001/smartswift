import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Newreport } from 'app/shared/model/newreport.model';
import { NewreportService } from './newreport.service';
import { NewreportComponent } from './newreport.component';
import { NewreportDetailComponent } from './newreport-detail.component';
import { NewreportUpdateComponent } from './newreport-update.component';
import { NewreportDeletePopupComponent } from './newreport-delete-dialog.component';
import { INewreport } from 'app/shared/model/newreport.model';

@Injectable({ providedIn: 'root' })
export class NewreportResolve implements Resolve<INewreport> {
    constructor(private service: NewreportService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((newreport: HttpResponse<Newreport>) => newreport.body));
        }
        return of(new Newreport());
    }
}

export const newreportRoute: Routes = [
    {
        path: 'newreport',
        component: NewreportComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Newreports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'newreport/:id/view',
        component: NewreportDetailComponent,
        resolve: {
            newreport: NewreportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Newreports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'newreport/new',
        component: NewreportUpdateComponent,
        resolve: {
            newreport: NewreportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Newreports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'newreport/:id/edit',
        component: NewreportUpdateComponent,
        resolve: {
            newreport: NewreportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Newreports'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const newreportPopupRoute: Routes = [
    {
        path: 'newreport/:id/delete',
        component: NewreportDeletePopupComponent,
        resolve: {
            newreport: NewreportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Newreports'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
