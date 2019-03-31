import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Options } from 'app/shared/model/options.model';
import { OptionsService } from './options.service';
import { OptionsComponent } from './options.component';
import { OptionsDetailComponent } from './options-detail.component';
import { OptionsUpdateComponent } from './options-update.component';
import { OptionsDeletePopupComponent } from './options-delete-dialog.component';
import { IOptions } from 'app/shared/model/options.model';

@Injectable({ providedIn: 'root' })
export class OptionsResolve implements Resolve<IOptions> {
    constructor(private service: OptionsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((options: HttpResponse<Options>) => options.body));
        }
        return of(new Options());
    }
}

export const optionsRoute: Routes = [
    {
        path: 'options',
        component: OptionsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Options'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'options/:id/view',
        component: OptionsDetailComponent,
        resolve: {
            options: OptionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Options'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'options/new',
        component: OptionsUpdateComponent,
        resolve: {
            options: OptionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Options'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'options/:id/edit',
        component: OptionsUpdateComponent,
        resolve: {
            options: OptionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Options'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const optionsPopupRoute: Routes = [
    {
        path: 'options/:id/delete',
        component: OptionsDeletePopupComponent,
        resolve: {
            options: OptionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Options'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
