import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    DevserviceComponent,
    DevserviceDetailComponent,
    DevserviceUpdateComponent,
    DevserviceDeletePopupComponent,
    DevserviceDeleteDialogComponent,
    devserviceRoute,
    devservicePopupRoute
} from './';

const ENTITY_STATES = [...devserviceRoute, ...devservicePopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DevserviceComponent,
        DevserviceDetailComponent,
        DevserviceUpdateComponent,
        DevserviceDeleteDialogComponent,
        DevserviceDeletePopupComponent
    ],
    entryComponents: [DevserviceComponent, DevserviceUpdateComponent, DevserviceDeleteDialogComponent, DevserviceDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftDevserviceModule {}
