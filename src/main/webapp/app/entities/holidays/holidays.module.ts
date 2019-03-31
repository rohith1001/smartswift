import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    HolidaysComponent,
    HolidaysDetailComponent,
    HolidaysUpdateComponent,
    HolidaysDeletePopupComponent,
    HolidaysDeleteDialogComponent,
    holidaysRoute,
    holidaysPopupRoute
} from './';

const ENTITY_STATES = [...holidaysRoute, ...holidaysPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HolidaysComponent,
        HolidaysDetailComponent,
        HolidaysUpdateComponent,
        HolidaysDeleteDialogComponent,
        HolidaysDeletePopupComponent
    ],
    entryComponents: [HolidaysComponent, HolidaysUpdateComponent, HolidaysDeleteDialogComponent, HolidaysDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftHolidaysModule {}
