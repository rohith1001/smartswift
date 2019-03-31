import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    PcdefectComponent,
    PcdefectDetailComponent,
    PcdefectUpdateComponent,
    PcdefectDeletePopupComponent,
    PcdefectDeleteDialogComponent,
    pcdefectRoute,
    pcdefectPopupRoute
} from './';

const ENTITY_STATES = [...pcdefectRoute, ...pcdefectPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PcdefectComponent,
        PcdefectDetailComponent,
        PcdefectUpdateComponent,
        PcdefectDeleteDialogComponent,
        PcdefectDeletePopupComponent
    ],
    entryComponents: [PcdefectComponent, PcdefectUpdateComponent, PcdefectDeleteDialogComponent, PcdefectDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftPcdefectModule {}
