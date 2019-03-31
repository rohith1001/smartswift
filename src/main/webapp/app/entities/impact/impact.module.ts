import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    ImpactComponent,
    ImpactDetailComponent,
    ImpactUpdateComponent,
    ImpactDeletePopupComponent,
    ImpactDeleteDialogComponent,
    impactRoute,
    impactPopupRoute
} from './';

const ENTITY_STATES = [...impactRoute, ...impactPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ImpactComponent, ImpactDetailComponent, ImpactUpdateComponent, ImpactDeleteDialogComponent, ImpactDeletePopupComponent],
    entryComponents: [ImpactComponent, ImpactUpdateComponent, ImpactDeleteDialogComponent, ImpactDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftImpactModule {}
