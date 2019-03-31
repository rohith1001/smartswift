import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    SamplebulkComponent,
    SamplebulkDetailComponent,
    SamplebulkUpdateComponent,
    SamplebulkDeletePopupComponent,
    SamplebulkDeleteDialogComponent,
    samplebulkRoute,
    samplebulkPopupRoute
} from './';

const ENTITY_STATES = [...samplebulkRoute, ...samplebulkPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SamplebulkComponent,
        SamplebulkDetailComponent,
        SamplebulkUpdateComponent,
        SamplebulkDeleteDialogComponent,
        SamplebulkDeletePopupComponent
    ],
    entryComponents: [SamplebulkComponent, SamplebulkUpdateComponent, SamplebulkDeleteDialogComponent, SamplebulkDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftSamplebulkModule {}
