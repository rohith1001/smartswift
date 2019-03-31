import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SmartswiftTicketModule } from './ticket/ticket.module';
import { SmartswiftStateModule } from './state/state.module';
import { SmartswiftDomainModule } from './domain/domain.module';
import { SmartswiftCategoryModule } from './category/category.module';
import { SmartswiftPriorityModule } from './priority/priority.module';
import { SmartswiftGroupModule } from './group/group.module';
import { SmartswiftIssuetypeModule } from './issuetype/issuetype.module';
import { SmartswiftIncidenttypeModule } from './incidenttype/incidenttype.module';
import { SmartswiftStkModule } from './stk/stk.module';
import { SmartswiftImpactModule } from './impact/impact.module';
import { SmartswiftL2queryModule } from './l-2-query/l-2-query.module';
import { SmartswiftIiaModule } from './iia/iia.module';
import { SmartswiftHldModule } from './hld/hld.module';
import { SmartswiftDevelopment_trackerModule } from './development-tracker/development-tracker.module';
import { SmartswiftDevserviceModule } from './devservice/devservice.module';
import { SmartswiftDevpriorityModule } from './devpriority/devpriority.module';
import { SmartswiftOptionsModule } from './options/options.module';
import { SmartswiftTest_trackerModule } from './test-tracker/test-tracker.module';
import { SmartswiftSeverityModule } from './severity/severity.module';
import { SmartswiftPc_trackerModule } from './pc-tracker/pc-tracker.module';
import { SmartswiftElf_statusModule } from './elf-status/elf-status.module';
import { SmartswiftConfigtypeModule } from './configtype/configtype.module';
import { SmartswiftPcincidentModule } from './pcincident/pcincident.module';
import { SmartswiftPcreleaseModule } from './pcrelease/pcrelease.module';
import { SmartswiftPcdefectModule } from './pcdefect/pcdefect.module';
import { SmartswiftResolvedModule } from './resolved/resolved.module';
import { SmartswiftPctrackerModule } from './pctracker/pctracker.module';
import { SmartswiftSamplebulkModule } from './samplebulk/samplebulk.module';
import { SmartswiftBulkincidentModule } from './bulkincident/bulkincident.module';
import { SmartswiftBulkdefectModule } from './bulkdefect/bulkdefect.module';
import { SmartswiftBulkreleaseModule } from './bulkrelease/bulkrelease.module';
import { SmartswiftHolidaysModule } from './holidays/holidays.module';
import { SmartswiftMonthlyreportModule } from './monthlyreport/monthlyreport.module';
import { SmartswiftConfigslasModule } from './configslas/configslas.module';
import { SmartswiftDashboardModule } from './dashboard/dashboard.module';
import { SmartswiftReportsModule } from './reports/reports.module';
import { SmartswiftNewreportModule } from './newreport/newreport.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SmartswiftTicketModule,
        SmartswiftStateModule,
        SmartswiftDomainModule,
        SmartswiftCategoryModule,
        SmartswiftPriorityModule,
        SmartswiftGroupModule,
        SmartswiftIssuetypeModule,
        SmartswiftIncidenttypeModule,
        SmartswiftStkModule,
        SmartswiftImpactModule,
        SmartswiftL2queryModule,
        SmartswiftIiaModule,
        SmartswiftHldModule,
        SmartswiftDevelopment_trackerModule,
        SmartswiftDevserviceModule,
        SmartswiftDevpriorityModule,
        SmartswiftOptionsModule,
        SmartswiftTest_trackerModule,
        SmartswiftSeverityModule,
        SmartswiftPc_trackerModule,
        SmartswiftElf_statusModule,
        SmartswiftConfigtypeModule,
        SmartswiftPcincidentModule,
        SmartswiftPcreleaseModule,
        SmartswiftPcdefectModule,
        SmartswiftResolvedModule,
        SmartswiftPctrackerModule,
        SmartswiftSamplebulkModule,
        SmartswiftBulkincidentModule,
        SmartswiftBulkdefectModule,
        SmartswiftBulkreleaseModule,
        SmartswiftHolidaysModule,
        SmartswiftMonthlyreportModule,
        SmartswiftConfigslasModule,
        SmartswiftDashboardModule,
        SmartswiftReportsModule,
        SmartswiftNewreportModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartswiftEntityModule {}
