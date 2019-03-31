import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Test_tracker } from 'app/shared/model/test-tracker.model';
import { Test_trackerService } from './test-tracker.service';
import { Test_trackerComponent } from './test-tracker.component';
import { Test_trackerDetailComponent } from './test-tracker-detail.component';
import { Test_trackerUpdateComponent } from './test-tracker-update.component';
import { Test_trackerDeletePopupComponent } from './test-tracker-delete-dialog.component';
import { ITest_tracker } from 'app/shared/model/test-tracker.model';

@Injectable({ providedIn: 'root' })
export class Test_trackerResolve implements Resolve<ITest_tracker> {
    constructor(private service: Test_trackerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((test_tracker: HttpResponse<Test_tracker>) => test_tracker.body));
        }
        return of(new Test_tracker());
    }
}

export const test_trackerRoute: Routes = [
    {
        path: 'test-tracker',
        component: Test_trackerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Test_trackers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'test-tracker/:id/view',
        component: Test_trackerDetailComponent,
        resolve: {
            test_tracker: Test_trackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Test_trackers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'test-tracker/new',
        component: Test_trackerUpdateComponent,
        resolve: {
            test_tracker: Test_trackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Test_trackers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'test-tracker/:id/edit',
        component: Test_trackerUpdateComponent,
        resolve: {
            test_tracker: Test_trackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Test_trackers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const test_trackerPopupRoute: Routes = [
    {
        path: 'test-tracker/:id/delete',
        component: Test_trackerDeletePopupComponent,
        resolve: {
            test_tracker: Test_trackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Test_trackers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
