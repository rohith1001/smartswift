import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    BulkincidentComponent,
    BulkincidentDetailComponent,
    BulkincidentUpdateComponent,
    BulkincidentDeletePopupComponent,
    BulkincidentDeleteDialogComponent,
    bulkincidentRoute,
    bulkincidentPopupRoute
} from './';

const ENTITY_STATES = [...bulkincidentRoute, ...bulkincidentPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BulkincidentComponent,
        BulkincidentDetailComponent,
        BulkincidentUpdateComponent,
        BulkincidentDeleteDialogComponent,
        BulkincidentDeletePopupComponent
    ],
    entryComponents: [
        BulkincidentComponent,
        BulkincidentUpdateComponent,
        BulkincidentDeleteDialogComponent,
        BulkincidentDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftBulkincidentModule {}
