import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Pcincident } from 'app/shared/model/pcincident.model';
import { PcincidentService } from './pcincident.service';
import { PcincidentComponent } from './pcincident.component';
import { PcincidentDetailComponent } from './pcincident-detail.component';
import { PcincidentUpdateComponent } from './pcincident-update.component';
import { PcincidentDeletePopupComponent } from './pcincident-delete-dialog.component';
import { IPcincident } from 'app/shared/model/pcincident.model';

@Injectable({ providedIn: 'root' })
export class PcincidentResolve implements Resolve<IPcincident> {
    constructor(private service: PcincidentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((pcincident: HttpResponse<Pcincident>) => pcincident.body));
        }
        return of(new Pcincident());
    }
}

export const pcincidentRoute: Routes = [
    {
        path: 'pcincident',
        component: PcincidentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pcincidents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pcincident/:id/view',
        component: PcincidentDetailComponent,
        resolve: {
            pcincident: PcincidentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pcincidents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pcincident/new',
        component: PcincidentUpdateComponent,
        resolve: {
            pcincident: PcincidentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pcincidents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pcincident/:id/edit',
        component: PcincidentUpdateComponent,
        resolve: {
            pcincident: PcincidentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pcincidents'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pcincidentPopupRoute: Routes = [
    {
        path: 'pcincident/:id/delete',
        component: PcincidentDeletePopupComponent,
        resolve: {
            pcincident: PcincidentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pcincidents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
