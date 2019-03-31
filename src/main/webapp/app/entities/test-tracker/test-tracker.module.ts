import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    Test_trackerComponent,
    Test_trackerDetailComponent,
    Test_trackerUpdateComponent,
    Test_trackerDeletePopupComponent,
    Test_trackerDeleteDialogComponent,
    test_trackerRoute,
    test_trackerPopupRoute
} from './';

const ENTITY_STATES = [...test_trackerRoute, ...test_trackerPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        Test_trackerComponent,
        Test_trackerDetailComponent,
        Test_trackerUpdateComponent,
        Test_trackerDeleteDialogComponent,
        Test_trackerDeletePopupComponent
    ],
    entryComponents: [
        Test_trackerComponent,
        Test_trackerUpdateComponent,
        Test_trackerDeleteDialogComponent,
        Test_trackerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftTest_trackerModule {}
