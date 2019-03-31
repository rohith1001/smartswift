import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    PcreleaseComponent,
    PcreleaseDetailComponent,
    PcreleaseUpdateComponent,
    PcreleaseDeletePopupComponent,
    PcreleaseDeleteDialogComponent,
    pcreleaseRoute,
    pcreleasePopupRoute
} from './';

const ENTITY_STATES = [...pcreleaseRoute, ...pcreleasePopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PcreleaseComponent,
        PcreleaseDetailComponent,
        PcreleaseUpdateComponent,
        PcreleaseDeleteDialogComponent,
        PcreleaseDeletePopupComponent
    ],
    entryComponents: [PcreleaseComponent, PcreleaseUpdateComponent, PcreleaseDeleteDialogComponent, PcreleaseDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftPcreleaseModule {}
