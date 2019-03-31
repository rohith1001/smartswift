import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Development_tracker } from 'app/shared/model/development-tracker.model';
import { Development_trackerService } from './development-tracker.service';
import { Development_trackerComponent } from './development-tracker.component';
import { Development_trackerDetailComponent } from './development-tracker-detail.component';
import { Development_trackerUpdateComponent } from './development-tracker-update.component';
import { Development_trackerDeletePopupComponent } from './development-tracker-delete-dialog.component';
import { IDevelopment_tracker } from 'app/shared/model/development-tracker.model';

@Injectable({ providedIn: 'root' })
export class Development_trackerResolve implements Resolve<IDevelopment_tracker> {
    constructor(private service: Development_trackerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((development_tracker: HttpResponse<Development_tracker>) => development_tracker.body));
        }
        return of(new Development_tracker());
    }
}

export const development_trackerRoute: Routes = [
    {
        path: 'development-tracker',
        component: Development_trackerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Development_trackers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'development-tracker/:id/view',
        component: Development_trackerDetailComponent,
        resolve: {
            development_tracker: Development_trackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Development_trackers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'development-tracker/new',
        component: Development_trackerUpdateComponent,
        resolve: {
            development_tracker: Development_trackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Development_trackers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'development-tracker/:id/edit',
        component: Development_trackerUpdateComponent,
        resolve: {
            development_tracker: Development_trackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Development_trackers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const development_trackerPopupRoute: Routes = [
    {
        path: 'development-tracker/:id/delete',
        component: Development_trackerDeletePopupComponent,
        resolve: {
            development_tracker: Development_trackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Development_trackers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
