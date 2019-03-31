import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IReports } from 'app/shared/model/reports.model';

@Component({
    selector: 'jhi-reports-detail',
    templateUrl: './reports-detail.component.html'
})
export class ReportsDetailComponent implements OnInit {
    reports: IReports;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reports }) => {
            this.reports = reports;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
