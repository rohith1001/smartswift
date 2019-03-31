import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    IncidenttypeComponent,
    IncidenttypeDetailComponent,
    IncidenttypeUpdateComponent,
    IncidenttypeDeletePopupComponent,
    IncidenttypeDeleteDialogComponent,
    incidenttypeRoute,
    incidenttypePopupRoute
} from './';

const ENTITY_STATES = [...incidenttypeRoute, ...incidenttypePopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IncidenttypeComponent,
        IncidenttypeDetailComponent,
        IncidenttypeUpdateComponent,
        IncidenttypeDeleteDialogComponent,
        IncidenttypeDeletePopupComponent
    ],
    entryComponents: [
        IncidenttypeComponent,
        IncidenttypeUpdateComponent,
        IncidenttypeDeleteDialogComponent,
        IncidenttypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftIncidenttypeModule {}
