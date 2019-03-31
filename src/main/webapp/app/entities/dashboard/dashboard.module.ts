import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    DashboardComponent,
    DashboardDetailComponent,
    DashboardUpdateComponent,
    DashboardDeletePopupComponent,
    DashboardDeleteDialogComponent,
    dashboardRoute,
    dashboardPopupRoute
} from './';

const ENTITY_STATES = [...dashboardRoute, ...dashboardPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DashboardComponent,
        DashboardDetailComponent,
        DashboardUpdateComponent,
        DashboardDeleteDialogComponent,
        DashboardDeletePopupComponent
    ],
    entryComponents: [DashboardComponent, DashboardUpdateComponent, DashboardDeleteDialogComponent, DashboardDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftDashboardModule {}
