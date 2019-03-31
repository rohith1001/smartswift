import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    IssuetypeComponent,
    IssuetypeDetailComponent,
    IssuetypeUpdateComponent,
    IssuetypeDeletePopupComponent,
    IssuetypeDeleteDialogComponent,
    issuetypeRoute,
    issuetypePopupRoute
} from './';

const ENTITY_STATES = [...issuetypeRoute, ...issuetypePopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IssuetypeComponent,
        IssuetypeDetailComponent,
        IssuetypeUpdateComponent,
        IssuetypeDeleteDialogComponent,
        IssuetypeDeletePopupComponent
    ],
    entryComponents: [IssuetypeComponent, IssuetypeUpdateComponent, IssuetypeDeleteDialogComponent, IssuetypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftIssuetypeModule {}
