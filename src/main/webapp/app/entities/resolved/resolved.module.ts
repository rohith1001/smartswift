import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    ResolvedComponent,
    ResolvedDetailComponent,
    ResolvedUpdateComponent,
    ResolvedDeletePopupComponent,
    ResolvedDeleteDialogComponent,
    resolvedRoute,
    resolvedPopupRoute
} from './';

const ENTITY_STATES = [...resolvedRoute, ...resolvedPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ResolvedComponent,
        ResolvedDetailComponent,
        ResolvedUpdateComponent,
        ResolvedDeleteDialogComponent,
        ResolvedDeletePopupComponent
    ],
    entryComponents: [ResolvedComponent, ResolvedUpdateComponent, ResolvedDeleteDialogComponent, ResolvedDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftResolvedModule {}
