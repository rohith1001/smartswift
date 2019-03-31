import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    ConfigtypeComponent,
    ConfigtypeDetailComponent,
    ConfigtypeUpdateComponent,
    ConfigtypeDeletePopupComponent,
    ConfigtypeDeleteDialogComponent,
    configtypeRoute,
    configtypePopupRoute
} from './';

const ENTITY_STATES = [...configtypeRoute, ...configtypePopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ConfigtypeComponent,
        ConfigtypeDetailComponent,
        ConfigtypeUpdateComponent,
        ConfigtypeDeleteDialogComponent,
        ConfigtypeDeletePopupComponent
    ],
    entryComponents: [ConfigtypeComponent, ConfigtypeUpdateComponent, ConfigtypeDeleteDialogComponent, ConfigtypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftConfigtypeModule {}
