import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT, DATE_FROM, DATE_TO, DATE_TIME_REGEX } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IDevelopment_tracker } from 'app/shared/model/development-tracker.model';
import { Development_trackerService } from './development-tracker.service';
import { IGroup } from 'app/shared/model/group.model';
import { GroupService } from 'app/entities/group';
import { IDevpriority } from 'app/shared/model/devpriority.model';
import { DevpriorityService } from 'app/entities/devpriority';
import { IDevservice } from 'app/shared/model/devservice.model';
import { DevserviceService } from 'app/entities/devservice';

@Component({
    selector: 'jhi-development-tracker-update',
    templateUrl: './development-tracker-update.component.html'
})
export class Development_trackerUpdateComponent implements OnInit {
    development_tracker: IDevelopment_tracker;
    isSaving: boolean;

    groups: IGroup[];

    devpriorities: IDevpriority[];

    devservices: IDevservice[];
    request_date: string;
    ack_date: string;
    cost_ready_date_actual: string;
    delivery_to_test_planned: string;
    delivery_to_test_actual: string;
    delivery_to_production_planned: string;
    delivery_to_production_actual: string;
    defect_date_raised: string;
    modified_time: string;
    sow_submission_date: string;
    finaldate: string;

    DF: string = DATE_FROM;
    DT: string = DATE_TO;
    DREGEX: string = DATE_TIME_REGEX;

    preventDefault(event){
        event.preventDefault();
    }

    constructor(
        private jhiAlertService: JhiAlertService,
        private development_trackerService: Development_trackerService,
        private groupService: GroupService,
        private devpriorityService: DevpriorityService,
        private devserviceService: DevserviceService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ development_tracker }) => {
            this.development_tracker = development_tracker;
            this.request_date =
                this.development_tracker.request_date != null ? this.development_tracker.request_date.format(DATE_TIME_FORMAT) : null;
            this.ack_date = this.development_tracker.ack_date != null ? this.development_tracker.ack_date.format(DATE_TIME_FORMAT) : null;
            this.cost_ready_date_actual =
                this.development_tracker.cost_ready_date_actual != null
                    ? this.development_tracker.cost_ready_date_actual.format(DATE_TIME_FORMAT)
                    : null;
            this.delivery_to_test_planned =
                this.development_tracker.delivery_to_test_planned != null
                    ? this.development_tracker.delivery_to_test_planned.format(DATE_TIME_FORMAT)
                    : null;
            this.delivery_to_test_actual =
                this.development_tracker.delivery_to_test_actual != null
                    ? this.development_tracker.delivery_to_test_actual.format(DATE_TIME_FORMAT)
                    : null;
            this.delivery_to_production_planned =
                this.development_tracker.delivery_to_production_planned != null
                    ? this.development_tracker.delivery_to_production_planned.format(DATE_TIME_FORMAT)
                    : null;
            this.delivery_to_production_actual =
                this.development_tracker.delivery_to_production_actual != null
                    ? this.development_tracker.delivery_to_production_actual.format(DATE_TIME_FORMAT)
                    : null;
            this.defect_date_raised =
                this.development_tracker.defect_date_raised != null
                    ? this.development_tracker.defect_date_raised.format(DATE_TIME_FORMAT)
                    : null;
            this.modified_time =
                this.development_tracker.modified_time != null ? this.development_tracker.modified_time.format(DATE_TIME_FORMAT) : null;
            this.sow_submission_date =
                this.development_tracker.sow_submission_date != null
                    ? this.development_tracker.sow_submission_date.format(DATE_TIME_FORMAT)
                    : null;
            this.finaldate =
                this.development_tracker.finaldate != null ? this.development_tracker.finaldate.format(DATE_TIME_FORMAT) : null;
        });
        this.groupService.query().subscribe(
            (res: HttpResponse<IGroup[]>) => {
                this.groups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.devpriorityService.query().subscribe(
            (res: HttpResponse<IDevpriority[]>) => {
                this.devpriorities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.devserviceService.query().subscribe(
            (res: HttpResponse<IDevservice[]>) => {
                this.devservices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.development_tracker.request_date = this.request_date != null ? moment(this.request_date, DATE_TIME_FORMAT) : null;
        this.development_tracker.ack_date = this.ack_date != null ? moment(this.ack_date, DATE_TIME_FORMAT) : null;
        this.development_tracker.cost_ready_date_actual =
            this.cost_ready_date_actual != null ? moment(this.cost_ready_date_actual, DATE_TIME_FORMAT) : null;
        this.development_tracker.delivery_to_test_planned =
            this.delivery_to_test_planned != null ? moment(this.delivery_to_test_planned, DATE_TIME_FORMAT) : null;
        this.development_tracker.delivery_to_test_actual =
            this.delivery_to_test_actual != null ? moment(this.delivery_to_test_actual, DATE_TIME_FORMAT) : null;
        this.development_tracker.delivery_to_production_planned =
            this.delivery_to_production_planned != null ? moment(this.delivery_to_production_planned, DATE_TIME_FORMAT) : null;
        this.development_tracker.delivery_to_production_actual =
            this.delivery_to_production_actual != null ? moment(this.delivery_to_production_actual, DATE_TIME_FORMAT) : null;
        this.development_tracker.defect_date_raised =
            this.defect_date_raised != null ? moment(this.defect_date_raised, DATE_TIME_FORMAT) : null;
        this.development_tracker.modified_time = this.modified_time != null ? moment(this.modified_time, DATE_TIME_FORMAT) : null;
        this.development_tracker.sow_submission_date =
            this.sow_submission_date != null ? moment(this.sow_submission_date, DATE_TIME_FORMAT) : null;
        this.development_tracker.finaldate = this.finaldate != null ? moment(this.finaldate, DATE_TIME_FORMAT) : null;
        if (this.development_tracker.id !== undefined) {
            this.subscribeToSaveResponse(this.development_trackerService.update(this.development_tracker));
        } else {
            this.subscribeToSaveResponse(this.development_trackerService.create(this.development_tracker));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDevelopment_tracker>>) {
        result.subscribe((res: HttpResponse<IDevelopment_tracker>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDevpriorityById(index: number, item: IDevpriority) {
        return item.id;
    }

    trackDevserviceById(index: number, item: IDevservice) {
        return item.id;
    }
}
