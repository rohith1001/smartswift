import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    Development_trackerComponent,
    Development_trackerDetailComponent,
    Development_trackerUpdateComponent,
    Development_trackerDeletePopupComponent,
    Development_trackerDeleteDialogComponent,
    development_trackerRoute,
    development_trackerPopupRoute
} from './';

const ENTITY_STATES = [...development_trackerRoute, ...development_trackerPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        Development_trackerComponent,
        Development_trackerDetailComponent,
        Development_trackerUpdateComponent,
        Development_trackerDeleteDialogComponent,
        Development_trackerDeletePopupComponent
    ],
    entryComponents: [
        Development_trackerComponent,
        Development_trackerUpdateComponent,
        Development_trackerDeleteDialogComponent,
        Development_trackerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftDevelopment_trackerModule {}
