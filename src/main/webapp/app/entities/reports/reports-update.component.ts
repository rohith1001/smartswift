import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils } from 'ng-jhipster';

import { IReports } from 'app/shared/model/reports.model';
import { ReportsService } from './reports.service';

@Component({
    selector: 'jhi-reports-update',
    templateUrl: './reports-update.component.html'
})
export class ReportsUpdateComponent implements OnInit {
    reports: IReports;
    isSaving: boolean;
    generated_on: string;

    constructor(private dataUtils: JhiDataUtils, private reportsService: ReportsService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reports }) => {
            this.reports = reports;
            this.generated_on = this.reports.generated_on != null ? this.reports.generated_on.format(DATE_TIME_FORMAT) : null;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.reports.generated_on = this.generated_on != null ? moment(this.generated_on, DATE_TIME_FORMAT) : null;
        if (this.reports.id !== undefined) {
            this.subscribeToSaveResponse(this.reportsService.update(this.reports));
        } else {
            this.subscribeToSaveResponse(this.reportsService.create(this.reports));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReports>>) {
        result.subscribe((res: HttpResponse<IReports>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
