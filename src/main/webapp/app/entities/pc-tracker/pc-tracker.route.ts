import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Pc_tracker } from 'app/shared/model/pc-tracker.model';
import { Pc_trackerService } from './pc-tracker.service';
import { Pc_trackerComponent } from './pc-tracker.component';
import { Pc_trackerDetailComponent } from './pc-tracker-detail.component';
import { Pc_trackerUpdateComponent } from './pc-tracker-update.component';
import { Pc_trackerDeletePopupComponent } from './pc-tracker-delete-dialog.component';
import { IPc_tracker } from 'app/shared/model/pc-tracker.model';

@Injectable({ providedIn: 'root' })
export class Pc_trackerResolve implements Resolve<IPc_tracker> {
    constructor(private service: Pc_trackerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((pc_tracker: HttpResponse<Pc_tracker>) => pc_tracker.body));
        }
        return of(new Pc_tracker());
    }
}

export const pc_trackerRoute: Routes = [
    {
        path: 'pc-tracker',
        component: Pc_trackerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pc_trackers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pc-tracker/:id/view',
        component: Pc_trackerDetailComponent,
        resolve: {
            pc_tracker: Pc_trackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pc_trackers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pc-tracker/new',
        component: Pc_trackerUpdateComponent,
        resolve: {
            pc_tracker: Pc_trackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pc_trackers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pc-tracker/:id/edit',
        component: Pc_trackerUpdateComponent,
        resolve: {
            pc_tracker: Pc_trackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pc_trackers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pc_trackerPopupRoute: Routes = [
    {
        path: 'pc-tracker/:id/delete',
        component: Pc_trackerDeletePopupComponent,
        resolve: {
            pc_tracker: Pc_trackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pc_trackers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
