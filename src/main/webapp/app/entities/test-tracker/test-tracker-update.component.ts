import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT, DATE_FROM, DATE_TO, DATE_TIME_REGEX } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ITest_tracker } from 'app/shared/model/test-tracker.model';
import { Test_trackerService } from './test-tracker.service';
import { IOptions } from 'app/shared/model/options.model';
import { OptionsService } from 'app/entities/options';
import { IGroup } from 'app/shared/model/group.model';
import { GroupService } from 'app/entities/group';
import { ISeverity } from 'app/shared/model/severity.model';
import { SeverityService } from 'app/entities/severity';

@Component({
    selector: 'jhi-test-tracker-update',
    templateUrl: './test-tracker-update.component.html'
})
export class Test_trackerUpdateComponent implements OnInit {
    test_tracker: ITest_tracker;
    isSaving: boolean;

    options: IOptions[];

    groups: IGroup[];

    severities: ISeverity[];
    detected_on_dateDp: any;
    first_fix_date: string;
    last_fix_date: string;
    fix_due_date: string;
    closing_date: string;
    modified_time: string;

    DF: string = DATE_FROM;
    DT: string = DATE_TO;
    DREGEX: string = DATE_TIME_REGEX;

    preventDefault(event){
        event.preventDefault();
    }

    constructor(
        private jhiAlertService: JhiAlertService,
        private test_trackerService: Test_trackerService,
        private optionsService: OptionsService,
        private groupService: GroupService,
        private severityService: SeverityService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ test_tracker }) => {
            this.test_tracker = test_tracker;
            this.first_fix_date =
                this.test_tracker.first_fix_date != null ? this.test_tracker.first_fix_date.format(DATE_TIME_FORMAT) : null;
            this.last_fix_date = this.test_tracker.last_fix_date != null ? this.test_tracker.last_fix_date.format(DATE_TIME_FORMAT) : null;
            this.fix_due_date = this.test_tracker.fix_due_date != null ? this.test_tracker.fix_due_date.format(DATE_TIME_FORMAT) : null;
            this.closing_date = this.test_tracker.closing_date != null ? this.test_tracker.closing_date.format(DATE_TIME_FORMAT) : null;
            this.modified_time = this.test_tracker.modified_time != null ? this.test_tracker.modified_time.format(DATE_TIME_FORMAT) : null;
        });
        this.optionsService.query().subscribe(
            (res: HttpResponse<IOptions[]>) => {
                this.options = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.groupService.query().subscribe(
            (res: HttpResponse<IGroup[]>) => {
                this.groups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.severityService.query().subscribe(
            (res: HttpResponse<ISeverity[]>) => {
                this.severities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.test_tracker.first_fix_date = this.first_fix_date != null ? moment(this.first_fix_date, DATE_TIME_FORMAT) : null;
        this.test_tracker.last_fix_date = this.last_fix_date != null ? moment(this.last_fix_date, DATE_TIME_FORMAT) : null;
        this.test_tracker.fix_due_date = this.fix_due_date != null ? moment(this.fix_due_date, DATE_TIME_FORMAT) : null;
        this.test_tracker.closing_date = this.closing_date != null ? moment(this.closing_date, DATE_TIME_FORMAT) : null;
        this.test_tracker.modified_time = this.modified_time != null ? moment(this.modified_time, DATE_TIME_FORMAT) : null;
        if (this.test_tracker.id !== undefined) {
            this.subscribeToSaveResponse(this.test_trackerService.update(this.test_tracker));
        } else {
            this.subscribeToSaveResponse(this.test_trackerService.create(this.test_tracker));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITest_tracker>>) {
        result.subscribe((res: HttpResponse<ITest_tracker>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOptionsById(index: number, item: IOptions) {
        return item.id;
    }

    trackGroupById(index: number, item: IGroup) {
        return item.id;
    }

    trackSeverityById(index: number, item: ISeverity) {
        return item.id;
    }
}
