import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IIia } from 'app/shared/model/iia.model';
import { IiaService } from './iia.service';
import { IGroup } from 'app/shared/model/group.model';
import { GroupService } from 'app/entities/group';
import { IResolved } from 'app/shared/model/resolved.model';
import { ResolvedService } from 'app/entities/resolved';

@Component({
    selector: 'jhi-iia-update',
    templateUrl: './iia-update.component.html'
})
export class IiaUpdateComponent implements OnInit {
    iia: IIia;
    isSaving: boolean;

    groups: IGroup[];

    resolveds: IResolved[];
    request_date: string;
    actual_acknowledgement_date: string;
    delivery_date_planned: string;
    delivery_date_actual: string;
    agreed_date: string;
    iia_resubmitted_date: string;
    hold_date: string;
    modified_time: string;
    finaldate: string;
    resubmission_request_date: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private iiaService: IiaService,
        private groupService: GroupService,
        private resolvedService: ResolvedService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ iia }) => {
            this.iia = iia;
            this.request_date = this.iia.request_date != null ? this.iia.request_date.format(DATE_TIME_FORMAT) : null;
            this.actual_acknowledgement_date =
                this.iia.actual_acknowledgement_date != null ? this.iia.actual_acknowledgement_date.format(DATE_TIME_FORMAT) : null;
            this.delivery_date_planned =
                this.iia.delivery_date_planned != null ? this.iia.delivery_date_planned.format(DATE_TIME_FORMAT) : null;
            this.delivery_date_actual =
                this.iia.delivery_date_actual != null ? this.iia.delivery_date_actual.format(DATE_TIME_FORMAT) : null;
            this.agreed_date = this.iia.agreed_date != null ? this.iia.agreed_date.format(DATE_TIME_FORMAT) : null;
            this.iia_resubmitted_date =
                this.iia.iia_resubmitted_date != null ? this.iia.iia_resubmitted_date.format(DATE_TIME_FORMAT) : null;
            this.hold_date = this.iia.hold_date != null ? this.iia.hold_date.format(DATE_TIME_FORMAT) : null;
            this.modified_time = this.iia.modified_time != null ? this.iia.modified_time.format(DATE_TIME_FORMAT) : null;
            this.finaldate = this.iia.finaldate != null ? this.iia.finaldate.format(DATE_TIME_FORMAT) : null;
            this.resubmission_request_date =
                this.iia.resubmission_request_date != null ? this.iia.resubmission_request_date.format(DATE_TIME_FORMAT) : null;
        });
        this.groupService.query().subscribe(
            (res: HttpResponse<IGroup[]>) => {
                this.groups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.resolvedService.query().subscribe(
            (res: HttpResponse<IResolved[]>) => {
                this.resolveds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.iia.request_date = this.request_date != null ? moment(this.request_date, DATE_TIME_FORMAT) : null;
        this.iia.actual_acknowledgement_date =
            this.actual_acknowledgement_date != null ? moment(this.actual_acknowledgement_date, DATE_TIME_FORMAT) : null;
        this.iia.delivery_date_planned = this.delivery_date_planned != null ? moment(this.delivery_date_planned, DATE_TIME_FORMAT) : null;
        this.iia.delivery_date_actual = this.delivery_date_actual != null ? moment(this.delivery_date_actual, DATE_TIME_FORMAT) : null;
        this.iia.agreed_date = this.agreed_date != null ? moment(this.agreed_date, DATE_TIME_FORMAT) : null;
        this.iia.iia_resubmitted_date = this.iia_resubmitted_date != null ? moment(this.iia_resubmitted_date, DATE_TIME_FORMAT) : null;
        this.iia.hold_date = this.hold_date != null ? moment(this.hold_date, DATE_TIME_FORMAT) : null;
        this.iia.modified_time = this.modified_time != null ? moment(this.modified_time, DATE_TIME_FORMAT) : null;
        this.iia.finaldate = this.finaldate != null ? moment(this.finaldate, DATE_TIME_FORMAT) : null;
        this.iia.resubmission_request_date = this.resubmission_request_date != null ? moment(this.resubmission_request_date, DATE_TIME_FORMAT) : null;
        if (this.iia.id !== undefined) {
            this.subscribeToSaveResponse(this.iiaService.update(this.iia));
        } else {
            this.subscribeToSaveResponse(this.iiaService.create(this.iia));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IIia>>) {
        result.subscribe((res: HttpResponse<IIia>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackGroupById(index: number, item: IGroup) {
        return item.id;
    }

    trackResolvedById(index: number, item: IResolved) {
        return item.id;
    }
}
