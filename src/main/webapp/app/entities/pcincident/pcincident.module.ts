import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    PcincidentComponent,
    PcincidentDetailComponent,
    PcincidentUpdateComponent,
    PcincidentDeletePopupComponent,
    PcincidentDeleteDialogComponent,
    pcincidentRoute,
    pcincidentPopupRoute
} from './';

const ENTITY_STATES = [...pcincidentRoute, ...pcincidentPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PcincidentComponent,
        PcincidentDetailComponent,
        PcincidentUpdateComponent,
        PcincidentDeleteDialogComponent,
        PcincidentDeletePopupComponent
    ],
    entryComponents: [PcincidentComponent, PcincidentUpdateComponent, PcincidentDeleteDialogComponent, PcincidentDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftPcincidentModule {}
