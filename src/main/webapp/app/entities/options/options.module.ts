import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    OptionsComponent,
    OptionsDetailComponent,
    OptionsUpdateComponent,
    OptionsDeletePopupComponent,
    OptionsDeleteDialogComponent,
    optionsRoute,
    optionsPopupRoute
} from './';

const ENTITY_STATES = [...optionsRoute, ...optionsPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OptionsComponent,
        OptionsDetailComponent,
        OptionsUpdateComponent,
        OptionsDeleteDialogComponent,
        OptionsDeletePopupComponent
    ],
    entryComponents: [OptionsComponent, OptionsUpdateComponent, OptionsDeleteDialogComponent, OptionsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftOptionsModule {}
