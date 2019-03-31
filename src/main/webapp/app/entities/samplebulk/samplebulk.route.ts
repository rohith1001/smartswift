import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Samplebulk } from 'app/shared/model/samplebulk.model';
import { SamplebulkService } from './samplebulk.service';
import { SamplebulkComponent } from './samplebulk.component';
import { SamplebulkDetailComponent } from './samplebulk-detail.component';
import { SamplebulkUpdateComponent } from './samplebulk-update.component';
import { SamplebulkDeletePopupComponent } from './samplebulk-delete-dialog.component';
import { ISamplebulk } from 'app/shared/model/samplebulk.model';

@Injectable({ providedIn: 'root' })
export class SamplebulkResolve implements Resolve<ISamplebulk> {
    constructor(private service: SamplebulkService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((samplebulk: HttpResponse<Samplebulk>) => samplebulk.body));
        }
        return of(new Samplebulk());
    }
}

export const samplebulkRoute: Routes = [
    {
        path: 'samplebulk',
        component: SamplebulkComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Samplebulks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'samplebulk/:id/view',
        component: SamplebulkDetailComponent,
        resolve: {
            samplebulk: SamplebulkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Samplebulks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'samplebulk/new',
        component: SamplebulkUpdateComponent,
        resolve: {
            samplebulk: SamplebulkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Samplebulks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'samplebulk/:id/edit',
        component: SamplebulkUpdateComponent,
        resolve: {
            samplebulk: SamplebulkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Samplebulks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const samplebulkPopupRoute: Routes = [
    {
        path: 'samplebulk/:id/delete',
        component: SamplebulkDeletePopupComponent,
        resolve: {
            samplebulk: SamplebulkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Samplebulks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
