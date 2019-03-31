import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    ConfigslasComponent,
    ConfigslasDetailComponent,
    ConfigslasUpdateComponent,
    ConfigslasDeletePopupComponent,
    ConfigslasDeleteDialogComponent,
    configslasRoute,
    configslasPopupRoute
} from './';

const ENTITY_STATES = [...configslasRoute, ...configslasPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ConfigslasComponent,
        ConfigslasDetailComponent,
        ConfigslasUpdateComponent,
        ConfigslasDeleteDialogComponent,
        ConfigslasDeletePopupComponent
    ],
    entryComponents: [ConfigslasComponent, ConfigslasUpdateComponent, ConfigslasDeleteDialogComponent, ConfigslasDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftConfigslasModule {}
