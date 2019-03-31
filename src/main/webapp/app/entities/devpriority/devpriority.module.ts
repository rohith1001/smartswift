import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    DevpriorityComponent,
    DevpriorityDetailComponent,
    DevpriorityUpdateComponent,
    DevpriorityDeletePopupComponent,
    DevpriorityDeleteDialogComponent,
    devpriorityRoute,
    devpriorityPopupRoute
} from './';

const ENTITY_STATES = [...devpriorityRoute, ...devpriorityPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DevpriorityComponent,
        DevpriorityDetailComponent,
        DevpriorityUpdateComponent,
        DevpriorityDeleteDialogComponent,
        DevpriorityDeletePopupComponent
    ],
    entryComponents: [DevpriorityComponent, DevpriorityUpdateComponent, DevpriorityDeleteDialogComponent, DevpriorityDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftDevpriorityModule {}
