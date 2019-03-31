import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Elf_status } from 'app/shared/model/elf-status.model';
import { Elf_statusService } from './elf-status.service';
import { Elf_statusComponent } from './elf-status.component';
import { Elf_statusDetailComponent } from './elf-status-detail.component';
import { Elf_statusUpdateComponent } from './elf-status-update.component';
import { Elf_statusDeletePopupComponent } from './elf-status-delete-dialog.component';
import { IElf_status } from 'app/shared/model/elf-status.model';

@Injectable({ providedIn: 'root' })
export class Elf_statusResolve implements Resolve<IElf_status> {
    constructor(private service: Elf_statusService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((elf_status: HttpResponse<Elf_status>) => elf_status.body));
        }
        return of(new Elf_status());
    }
}

export const elf_statusRoute: Routes = [
    {
        path: 'elf-status',
        component: Elf_statusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Elf_statuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'elf-status/:id/view',
        component: Elf_statusDetailComponent,
        resolve: {
            elf_status: Elf_statusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Elf_statuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'elf-status/new',
        component: Elf_statusUpdateComponent,
        resolve: {
            elf_status: Elf_statusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Elf_statuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'elf-status/:id/edit',
        component: Elf_statusUpdateComponent,
        resolve: {
            elf_status: Elf_statusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Elf_statuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const elf_statusPopupRoute: Routes = [
    {
        path: 'elf-status/:id/delete',
        component: Elf_statusDeletePopupComponent,
        resolve: {
            elf_status: Elf_statusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Elf_statuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
