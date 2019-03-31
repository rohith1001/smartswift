import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    BulkreleaseComponent,
    BulkreleaseDetailComponent,
    BulkreleaseUpdateComponent,
    BulkreleaseDeletePopupComponent,
    BulkreleaseDeleteDialogComponent,
    bulkreleaseRoute,
    bulkreleasePopupRoute
} from './';

const ENTITY_STATES = [...bulkreleaseRoute, ...bulkreleasePopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BulkreleaseComponent,
        BulkreleaseDetailComponent,
        BulkreleaseUpdateComponent,
        BulkreleaseDeleteDialogComponent,
        BulkreleaseDeletePopupComponent
    ],
    entryComponents: [BulkreleaseComponent, BulkreleaseUpdateComponent, BulkreleaseDeleteDialogComponent, BulkreleaseDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftBulkreleaseModule {}
