import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    Pc_trackerComponent,
    Pc_trackerDetailComponent,
    Pc_trackerUpdateComponent,
    Pc_trackerDeletePopupComponent,
    Pc_trackerDeleteDialogComponent,
    pc_trackerRoute,
    pc_trackerPopupRoute
} from './';

const ENTITY_STATES = [...pc_trackerRoute, ...pc_trackerPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        Pc_trackerComponent,
        Pc_trackerDetailComponent,
        Pc_trackerUpdateComponent,
        Pc_trackerDeleteDialogComponent,
        Pc_trackerDeletePopupComponent
    ],
    entryComponents: [Pc_trackerComponent, Pc_trackerUpdateComponent, Pc_trackerDeleteDialogComponent, Pc_trackerDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftPc_trackerModule {}
