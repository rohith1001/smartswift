import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    ReportsComponent,
    ReportsDetailComponent,
    ReportsUpdateComponent,
    ReportsDeletePopupComponent,
    ReportsDeleteDialogComponent,
    reportsRoute,
    reportsPopupRoute
} from './';

const ENTITY_STATES = [...reportsRoute, ...reportsPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReportsComponent,
        ReportsDetailComponent,
        ReportsUpdateComponent,
        ReportsDeleteDialogComponent,
        ReportsDeletePopupComponent
    ],
    entryComponents: [ReportsComponent, ReportsUpdateComponent, ReportsDeleteDialogComponent, ReportsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftReportsModule {}
