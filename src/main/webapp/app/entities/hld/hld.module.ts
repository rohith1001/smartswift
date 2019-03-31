import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    HldComponent,
    HldDetailComponent,
    HldUpdateComponent,
    HldDeletePopupComponent,
    HldDeleteDialogComponent,
    hldRoute,
    hldPopupRoute
} from './';

const ENTITY_STATES = [...hldRoute, ...hldPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [HldComponent, HldDetailComponent, HldUpdateComponent, HldDeleteDialogComponent, HldDeletePopupComponent],
    entryComponents: [HldComponent, HldUpdateComponent, HldDeleteDialogComponent, HldDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftHldModule {}
