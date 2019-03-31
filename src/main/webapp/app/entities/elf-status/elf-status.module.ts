import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartswiftSharedModule } from 'app/shared';
import {
    Elf_statusComponent,
    Elf_statusDetailComponent,
    Elf_statusUpdateComponent,
    Elf_statusDeletePopupComponent,
    Elf_statusDeleteDialogComponent,
    elf_statusRoute,
    elf_statusPopupRoute
} from './';

const ENTITY_STATES = [...elf_statusRoute, ...elf_statusPopupRoute];

@NgModule({
    imports: [SmartswiftSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        Elf_statusComponent,
        Elf_statusDetailComponent,
        Elf_statusUpdateComponent,
        Elf_statusDeleteDialogComponent,
        Elf_statusDeletePopupComponent
    ],
    entryComponents: [Elf_statusComponent, Elf_statusUpdateComponent, Elf_statusDeleteDialogComponent, Elf_statusDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftElf_statusModule {}
