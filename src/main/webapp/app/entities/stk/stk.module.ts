import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    StkComponent,
    StkDetailComponent,
    StkUpdateComponent,
    StkDeletePopupComponent,
    StkDeleteDialogComponent,
    stkRoute,
    stkPopupRoute
} from './';

const ENTITY_STATES = [...stkRoute, ...stkPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [StkComponent, StkDetailComponent, StkUpdateComponent, StkDeleteDialogComponent, StkDeletePopupComponent],
    entryComponents: [StkComponent, StkUpdateComponent, StkDeleteDialogComponent, StkDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftStkModule {}
