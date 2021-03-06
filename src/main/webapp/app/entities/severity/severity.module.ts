import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    SeverityComponent,
    SeverityDetailComponent,
    SeverityUpdateComponent,
    SeverityDeletePopupComponent,
    SeverityDeleteDialogComponent,
    severityRoute,
    severityPopupRoute
} from './';

const ENTITY_STATES = [...severityRoute, ...severityPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SeverityComponent,
        SeverityDetailComponent,
        SeverityUpdateComponent,
        SeverityDeleteDialogComponent,
        SeverityDeletePopupComponent
    ],
    entryComponents: [SeverityComponent, SeverityUpdateComponent, SeverityDeleteDialogComponent, SeverityDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftSeverityModule {}
