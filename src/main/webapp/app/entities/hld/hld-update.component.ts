import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT, DATE_FROM, DATE_TO, DATE_TIME_REGEX } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IHld } from 'app/shared/model/hld.model';
import { HldService } from './hld.service';
import { IGroup } from 'app/shared/model/group.model';
import { GroupService } from 'app/entities/group';

@Component({
    selector: 'jhi-hld-update',
    templateUrl: './hld-update.component.html'
})
export class HldUpdateComponent implements OnInit {
    hld: IHld;
    isSaving: boolean;

    groups: IGroup[];
    request_date: string;
    actual_acknowledgement_date: string;
    delivery_date_planned: string;
    delivery_date_actual: string;
    agreed_date: string;
    hold_date: string;
    modified_time: string;
    wif_submission_date: string;
    finaldate: string;

    DF: string = DATE_FROM;
    DT: string = DATE_TO;
    DREGEX: string = DATE_TIME_REGEX;

    preventDefault(event){
        event.preventDefault();
    }

    constructor(
        private jhiAlertService: JhiAlertService,
        private hldService: HldService,
        private groupService: GroupService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ hld }) => {
            this.hld = hld;
            this.request_date = this.hld.request_date != null ? this.hld.request_date.format(DATE_TIME_FORMAT) : null;
            this.actual_acknowledgement_date =
                this.hld.actual_acknowledgement_date != null ? this.hld.actual_acknowledgement_date.format(DATE_TIME_FORMAT) : null;
            this.delivery_date_planned =
                this.hld.delivery_date_planned != null ? this.hld.delivery_date_planned.format(DATE_TIME_FORMAT) : null;
            this.delivery_date_actual =
                this.hld.delivery_date_actual != null ? this.hld.delivery_date_actual.format(DATE_TIME_FORMAT) : null;
            this.agreed_date = this.hld.agreed_date != null ? this.hld.agreed_date.format(DATE_TIME_FORMAT) : null;
            this.hold_date = this.hld.hold_date != null ? this.hld.hold_date.format(DATE_TIME_FORMAT) : null;
            this.modified_time = this.hld.modified_time != null ? this.hld.modified_time.format(DATE_TIME_FORMAT) : null;
            this.wif_submission_date = this.hld.wif_submission_date != null ? this.hld.wif_submission_date.format(DATE_TIME_FORMAT) : null;
            this.finaldate = this.hld.finaldate != null ? this.hld.finaldate.format(DATE_TIME_FORMAT) : null;
        });
        this.groupService.query().subscribe(
            (res: HttpResponse<IGroup[]>) => {
                this.groups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.hld.request_date = this.request_date != null ? moment(this.request_date, DATE_TIME_FORMAT) : null;
        this.hld.actual_acknowledgement_date =
            this.actual_acknowledgement_date != null ? moment(this.actual_acknowledgement_date, DATE_TIME_FORMAT) : null;
        this.hld.delivery_date_planned = this.delivery_date_planned != null ? moment(this.delivery_date_planned, DATE_TIME_FORMAT) : null;
        this.hld.delivery_date_actual = this.delivery_date_actual != null ? moment(this.delivery_date_actual, DATE_TIME_FORMAT) : null;
        this.hld.agreed_date = this.agreed_date != null ? moment(this.agreed_date, DATE_TIME_FORMAT) : null;
        this.hld.hold_date = this.hold_date != null ? moment(this.hold_date, DATE_TIME_FORMAT) : null;
        this.hld.modified_time = this.modified_time != null ? moment(this.modified_time, DATE_TIME_FORMAT) : null;
        this.hld.wif_submission_date = this.wif_submission_date != null ? moment(this.wif_submission_date, DATE_TIME_FORMAT) : null;
        this.hld.finaldate = this.finaldate != null ? moment(this.finaldate, DATE_TIME_FORMAT) : null;
        if (this.hld.id !== undefined) {
            this.subscribeToSaveResponse(this.hldService.update(this.hld));
        } else {
            this.subscribeToSaveResponse(this.hldService.create(this.hld));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHld>>) {
        result.subscribe((res: HttpResponse<IHld>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
