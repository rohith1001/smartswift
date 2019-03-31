import { NgModule } from '@angular/core';

import { SmartswiftSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [SmartswiftSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [SmartswiftSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class SmartswiftSharedCommonModule {}
