import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    IiaComponent,
    IiaDetailComponent,
    IiaUpdateComponent,
    IiaDeletePopupComponent,
    IiaDeleteDialogComponent,
    iiaRoute,
    iiaPopupRoute
} from './';

const ENTITY_STATES = [...iiaRoute, ...iiaPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [IiaComponent, IiaDetailComponent, IiaUpdateComponent, IiaDeleteDialogComponent, IiaDeletePopupComponent],
    entryComponents: [IiaComponent, IiaUpdateComponent, IiaDeleteDialogComponent, IiaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftIiaModule {}
