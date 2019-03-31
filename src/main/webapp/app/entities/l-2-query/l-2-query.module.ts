import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    L2queryComponent,
    L2queryDetailComponent,
    L2queryUpdateComponent,
    L2queryDeletePopupComponent,
    L2queryDeleteDialogComponent,
    l2queryRoute,
    l2queryPopupRoute
} from './';

const ENTITY_STATES = [...l2queryRoute, ...l2queryPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        L2queryComponent,
        L2queryDetailComponent,
        L2queryUpdateComponent,
        L2queryDeleteDialogComponent,
        L2queryDeletePopupComponent
    ],
    entryComponents: [L2queryComponent, L2queryUpdateComponent, L2queryDeleteDialogComponent, L2queryDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftL2queryModule {}
