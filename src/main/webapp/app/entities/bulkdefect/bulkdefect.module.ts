import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    BulkdefectComponent,
    BulkdefectDetailComponent,
    BulkdefectUpdateComponent,
    BulkdefectDeletePopupComponent,
    BulkdefectDeleteDialogComponent,
    bulkdefectRoute,
    bulkdefectPopupRoute
} from './';

const ENTITY_STATES = [...bulkdefectRoute, ...bulkdefectPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BulkdefectComponent,
        BulkdefectDetailComponent,
        BulkdefectUpdateComponent,
        BulkdefectDeleteDialogComponent,
        BulkdefectDeletePopupComponent
    ],
    entryComponents: [BulkdefectComponent, BulkdefectUpdateComponent, BulkdefectDeleteDialogComponent, BulkdefectDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftBulkdefectModule {}
