import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    MonthlyreportComponent,
    MonthlyreportDetailComponent,
    MonthlyreportUpdateComponent,
    MonthlyreportDeletePopupComponent,
    MonthlyreportDeleteDialogComponent,
    monthlyreportRoute,
    monthlyreportPopupRoute
} from './';

const ENTITY_STATES = [...monthlyreportRoute, ...monthlyreportPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MonthlyreportComponent,
        MonthlyreportDetailComponent,
        MonthlyreportUpdateComponent,
        MonthlyreportDeleteDialogComponent,
        MonthlyreportDeletePopupComponent
    ],
    entryComponents: [
        MonthlyreportComponent,
        MonthlyreportUpdateComponent,
        MonthlyreportDeleteDialogComponent,
        MonthlyreportDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftMonthlyreportModule {}
