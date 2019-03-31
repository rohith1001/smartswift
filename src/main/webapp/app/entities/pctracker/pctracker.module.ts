import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    PctrackerComponent,
    PctrackerDetailComponent,
    PctrackerUpdateComponent,
    PctrackerDeletePopupComponent,
    PctrackerDeleteDialogComponent,
    pctrackerRoute,
    pctrackerPopupRoute
} from './';

const ENTITY_STATES = [...pctrackerRoute, ...pctrackerPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PctrackerComponent,
        PctrackerDetailComponent,
        PctrackerUpdateComponent,
        PctrackerDeleteDialogComponent,
        PctrackerDeletePopupComponent
    ],
    entryComponents: [PctrackerComponent, PctrackerUpdateComponent, PctrackerDeleteDialogComponent, PctrackerDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftPctrackerModule {}
