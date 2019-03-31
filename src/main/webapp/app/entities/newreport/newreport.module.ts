import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    NewreportComponent,
    NewreportDetailComponent,
    NewreportUpdateComponent,
    NewreportDeletePopupComponent,
    NewreportDeleteDialogComponent,
    newreportRoute,
    newreportPopupRoute
} from './';

const ENTITY_STATES = [...newreportRoute, ...newreportPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NewreportComponent,
        NewreportDetailComponent,
        NewreportUpdateComponent,
        NewreportDeleteDialogComponent,
        NewreportDeletePopupComponent
    ],
    entryComponents: [NewreportComponent, NewreportUpdateComponent, NewreportDeleteDialogComponent, NewreportDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftNewreportModule {}
