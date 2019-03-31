import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Hld } from 'app/shared/model/hld.model';
import { HldService } from './hld.service';
import { HldComponent } from './hld.component';
import { HldDetailComponent } from './hld-detail.component';
import { HldUpdateComponent } from './hld-update.component';
import { HldDeletePopupComponent } from './hld-delete-dialog.component';
import { IHld } from 'app/shared/model/hld.model';

@Injectable({ providedIn: 'root' })
export class HldResolve implements Resolve<IHld> {
    constructor(private service: HldService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((hld: HttpResponse<Hld>) => hld.body));
        }
        return of(new Hld());
    }
}

export const hldRoute: Routes = [
    {
        path: 'hld',
        component: HldComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Hlds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hld/:id/view',
        component: HldDetailComponent,
        resolve: {
            hld: HldResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Hlds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hld/new',
        component: HldUpdateComponent,
        resolve: {
            hld: HldResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Hlds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hld/:id/edit',
        component: HldUpdateComponent,
        resolve: {
            hld: HldResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Hlds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hldPopupRoute: Routes = [
    {
        path: 'hld/:id/delete',
        component: HldDeletePopupComponent,
        resolve: {
            hld: HldResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Hlds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
