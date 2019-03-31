import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Domain } from 'app/shared/model/domain.model';
import { DomainService } from './domain.service';
import { DomainComponent } from './domain.component';
import { DomainDetailComponent } from './domain-detail.component';
import { DomainUpdateComponent } from './domain-update.component';
import { DomainDeletePopupComponent } from './domain-delete-dialog.component';
import { IDomain } from 'app/shared/model/domain.model';

@Injectable({ providedIn: 'root' })
export class DomainResolve implements Resolve<IDomain> {
    constructor(private service: DomainService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((domain: HttpResponse<Domain>) => domain.body));
        }
        return of(new Domain());
    }
}

export const domainRoute: Routes = [
    {
        path: 'domain',
        component: DomainComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Domains'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'domain/:id/view',
        component: DomainDetailComponent,
        resolve: {
            domain: DomainResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Domains'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'domain/new',
        component: DomainUpdateComponent,
        resolve: {
            domain: DomainResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Domains'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'domain/:id/edit',
        component: DomainUpdateComponent,
        resolve: {
            domain: DomainResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Domains'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const domainPopupRoute: Routes = [
    {
        path: 'domain/:id/delete',
        component: DomainDeletePopupComponent,
        resolve: {
            domain: DomainResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Domains'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
