import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    DomainComponent,
    DomainDetailComponent,
    DomainUpdateComponent,
    DomainDeletePopupComponent,
    DomainDeleteDialogComponent,
    domainRoute,
    domainPopupRoute
} from './';

const ENTITY_STATES = [...domainRoute, ...domainPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [DomainComponent, DomainDetailComponent, DomainUpdateComponent, DomainDeleteDialogComponent, DomainDeletePopupComponent],
    entryComponents: [DomainComponent, DomainUpdateComponent, DomainDeleteDialogComponent, DomainDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftDomainModule {}
