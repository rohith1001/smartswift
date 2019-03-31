import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Pctracker } from 'app/shared/model/pctracker.model';
import { PctrackerService } from './pctracker.service';
import { PctrackerComponent } from './pctracker.component';
import { PctrackerDetailComponent } from './pctracker-detail.component';
import { PctrackerUpdateComponent } from './pctracker-update.component';
import { PctrackerDeletePopupComponent } from './pctracker-delete-dialog.component';
import { IPctracker } from 'app/shared/model/pctracker.model';

@Injectable({ providedIn: 'root' })
export class PctrackerResolve implements Resolve<IPctracker> {
    constructor(private service: PctrackerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((pctracker: HttpResponse<Pctracker>) => pctracker.body));
        }
        return of(new Pctracker());
    }
}

export const pctrackerRoute: Routes = [
    {
        path: 'pctracker',
        component: PctrackerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pctrackers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pctracker/:id/view',
        component: PctrackerDetailComponent,
        resolve: {
            pctracker: PctrackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pctrackers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pctracker/new',
        component: PctrackerUpdateComponent,
        resolve: {
            pctracker: PctrackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pctrackers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pctracker/:id/edit',
        component: PctrackerUpdateComponent,
        resolve: {
            pctracker: PctrackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pctrackers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pctrackerPopupRoute: Routes = [
    {
        path: 'pctracker/:id/delete',
        component: PctrackerDeletePopupComponent,
        resolve: {
            pctracker: PctrackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pctrackers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
