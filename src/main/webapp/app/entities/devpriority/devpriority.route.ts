import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Devpriority } from 'app/shared/model/devpriority.model';
import { DevpriorityService } from './devpriority.service';
import { DevpriorityComponent } from './devpriority.component';
import { DevpriorityDetailComponent } from './devpriority-detail.component';
import { DevpriorityUpdateComponent } from './devpriority-update.component';
import { DevpriorityDeletePopupComponent } from './devpriority-delete-dialog.component';
import { IDevpriority } from 'app/shared/model/devpriority.model';

@Injectable({ providedIn: 'root' })
export class DevpriorityResolve implements Resolve<IDevpriority> {
    constructor(private service: DevpriorityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((devpriority: HttpResponse<Devpriority>) => devpriority.body));
        }
        return of(new Devpriority());
    }
}

export const devpriorityRoute: Routes = [
    {
        path: 'devpriority',
        component: DevpriorityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Devpriorities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'devpriority/:id/view',
        component: DevpriorityDetailComponent,
        resolve: {
            devpriority: DevpriorityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Devpriorities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'devpriority/new',
        component: DevpriorityUpdateComponent,
        resolve: {
            devpriority: DevpriorityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Devpriorities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'devpriority/:id/edit',
        component: DevpriorityUpdateComponent,
        resolve: {
            devpriority: DevpriorityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Devpriorities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const devpriorityPopupRoute: Routes = [
    {
        path: 'devpriority/:id/delete',
        component: DevpriorityDeletePopupComponent,
        resolve: {
            devpriority: DevpriorityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Devpriorities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
