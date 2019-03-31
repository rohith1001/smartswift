import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPc_tracker } from 'app/shared/model/pc-tracker.model';
import { Pc_trackerService } from './pc-tracker.service';
import { IElf_status } from 'app/shared/model/elf-status.model';
import { Elf_statusService } from 'app/entities/elf-status';
import { IConfigtype } from 'app/shared/model/configtype.model';
import { ConfigtypeService } from 'app/entities/configtype';

@Component({
    selector: 'jhi-pc-tracker-update',
    templateUrl: './pc-tracker-update.component.html'
})
export class Pc_trackerUpdateComponent implements OnInit {
    pc_tracker: IPc_tracker;
    isSaving: boolean;

    elf_statuses: IElf_status[];

    configtypes: IConfigtype[];
    date_received: string;
    iia_delivery_date_planned: string;
    iia_delivery_date_actual: string;
    dcd_delivery_date_planned: string;
    dcd_delivery_date_actual: string;
    wr_acknowledge_date_planned: string;
    wr_acknowledge_date_actual: string;
    wr_costready_date_planned: string;
    wr_costready_date_actual: string;
    hlcd_delivery_date_planned: string;
    hlcd_delivery_date_actual: string;
    test_ready_date_planned: string;
    test_ready_date_actual: string;
    launch_date_planned: string;
    launch_date_actual: string;
    delivery_date_planned: string;
    delivery_date_actual: string;
    modified_time: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private pc_trackerService: Pc_trackerService,
        private elf_statusService: Elf_statusService,
        private configtypeService: ConfigtypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pc_tracker }) => {
            this.pc_tracker = pc_tracker;
            this.date_received = this.pc_tracker.date_received != null ? this.pc_tracker.date_received.format(DATE_TIME_FORMAT) : null;
            this.iia_delivery_date_planned =
                this.pc_tracker.iia_delivery_date_planned != null
                    ? this.pc_tracker.iia_delivery_date_planned.format(DATE_TIME_FORMAT)
                    : null;
            this.iia_delivery_date_actual =
                this.pc_tracker.iia_delivery_date_actual != null ? this.pc_tracker.iia_delivery_date_actual.format(DATE_TIME_FORMAT) : null;
            this.dcd_delivery_date_planned =
                this.pc_tracker.dcd_delivery_date_planned != null
                    ? this.pc_tracker.dcd_delivery_date_planned.format(DATE_TIME_FORMAT)
                    : null;
            this.dcd_delivery_date_actual =
                this.pc_tracker.dcd_delivery_date_actual != null ? this.pc_tracker.dcd_delivery_date_actual.format(DATE_TIME_FORMAT) : null;
            this.wr_acknowledge_date_planned =
                this.pc_tracker.wr_acknowledge_date_planned != null
                    ? this.pc_tracker.wr_acknowledge_date_planned.format(DATE_TIME_FORMAT)
                    : null;
            this.wr_acknowledge_date_actual =
                this.pc_tracker.wr_acknowledge_date_actual != null
                    ? this.pc_tracker.wr_acknowledge_date_actual.format(DATE_TIME_FORMAT)
                    : null;
            this.wr_costready_date_planned =
                this.pc_tracker.wr_costready_date_planned != null
                    ? this.pc_tracker.wr_costready_date_planned.format(DATE_TIME_FORMAT)
                    : null;
            this.wr_costready_date_actual =
                this.pc_tracker.wr_costready_date_actual != null ? this.pc_tracker.wr_costready_date_actual.format(DATE_TIME_FORMAT) : null;
            this.hlcd_delivery_date_planned =
                this.pc_tracker.hlcd_delivery_date_planned != null
                    ? this.pc_tracker.hlcd_delivery_date_planned.format(DATE_TIME_FORMAT)
                    : null;
            this.hlcd_delivery_date_actual =
                this.pc_tracker.hlcd_delivery_date_actual != null
                    ? this.pc_tracker.hlcd_delivery_date_actual.format(DATE_TIME_FORMAT)
                    : null;
            this.test_ready_date_planned =
                this.pc_tracker.test_ready_date_planned != null ? this.pc_tracker.test_ready_date_planned.format(DATE_TIME_FORMAT) : null;
            this.test_ready_date_actual =
                this.pc_tracker.test_ready_date_actual != null ? this.pc_tracker.test_ready_date_actual.format(DATE_TIME_FORMAT) : null;
            this.launch_date_planned =
                this.pc_tracker.launch_date_planned != null ? this.pc_tracker.launch_date_planned.format(DATE_TIME_FORMAT) : null;
            this.launch_date_actual =
                this.pc_tracker.launch_date_actual != null ? this.pc_tracker.launch_date_actual.format(DATE_TIME_FORMAT) : null;
            this.delivery_date_planned =
                this.pc_tracker.delivery_date_planned != null ? this.pc_tracker.delivery_date_planned.format(DATE_TIME_FORMAT) : null;
            this.delivery_date_actual =
                this.pc_tracker.delivery_date_actual != null ? this.pc_tracker.delivery_date_actual.format(DATE_TIME_FORMAT) : null;
            this.modified_time = this.pc_tracker.modified_time != null ? this.pc_tracker.modified_time.format(DATE_TIME_FORMAT) : null;
        });
        this.elf_statusService.query().subscribe(
            (res: HttpResponse<IElf_status[]>) => {
                this.elf_statuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.configtypeService.query().subscribe(
            (res: HttpResponse<IConfigtype[]>) => {
                this.configtypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.pc_tracker.date_received = this.date_received != null ? moment(this.date_received, DATE_TIME_FORMAT) : null;
        this.pc_tracker.iia_delivery_date_planned =
            this.iia_delivery_date_planned != null ? moment(this.iia_delivery_date_planned, DATE_TIME_FORMAT) : null;
        this.pc_tracker.iia_delivery_date_actual =
            this.iia_delivery_date_actual != null ? moment(this.iia_delivery_date_actual, DATE_TIME_FORMAT) : null;
        this.pc_tracker.dcd_delivery_date_planned =
            this.dcd_delivery_date_planned != null ? moment(this.dcd_delivery_date_planned, DATE_TIME_FORMAT) : null;
        this.pc_tracker.dcd_delivery_date_actual =
            this.dcd_delivery_date_actual != null ? moment(this.dcd_delivery_date_actual, DATE_TIME_FORMAT) : null;
        this.pc_tracker.wr_acknowledge_date_planned =
            this.wr_acknowledge_date_planned != null ? moment(this.wr_acknowledge_date_planned, DATE_TIME_FORMAT) : null;
        this.pc_tracker.wr_acknowledge_date_actual =
            this.wr_acknowledge_date_actual != null ? moment(this.wr_acknowledge_date_actual, DATE_TIME_FORMAT) : null;
        this.pc_tracker.wr_costready_date_planned =
            this.wr_costready_date_planned != null ? moment(this.wr_costready_date_planned, DATE_TIME_FORMAT) : null;
        this.pc_tracker.wr_costready_date_actual =
            this.wr_costready_date_actual != null ? moment(this.wr_costready_date_actual, DATE_TIME_FORMAT) : null;
        this.pc_tracker.hlcd_delivery_date_planned =
            this.hlcd_delivery_date_planned != null ? moment(this.hlcd_delivery_date_planned, DATE_TIME_FORMAT) : null;
        this.pc_tracker.hlcd_delivery_date_actual =
            this.hlcd_delivery_date_actual != null ? moment(this.hlcd_delivery_date_actual, DATE_TIME_FORMAT) : null;
        this.pc_tracker.test_ready_date_planned =
            this.test_ready_date_planned != null ? moment(this.test_ready_date_planned, DATE_TIME_FORMAT) : null;
        this.pc_tracker.test_ready_date_actual =
            this.test_ready_date_actual != null ? moment(this.test_ready_date_actual, DATE_TIME_FORMAT) : null;
        this.pc_tracker.launch_date_planned = this.launch_date_planned != null ? moment(this.launch_date_planned, DATE_TIME_FORMAT) : null;
        this.pc_tracker.launch_date_actual = this.launch_date_actual != null ? moment(this.launch_date_actual, DATE_TIME_FORMAT) : null;
        this.pc_tracker.delivery_date_planned =
            this.delivery_date_planned != null ? moment(this.delivery_date_planned, DATE_TIME_FORMAT) : null;
        this.pc_tracker.delivery_date_actual =
            this.delivery_date_actual != null ? moment(this.delivery_date_actual, DATE_TIME_FORMAT) : null;
        this.pc_tracker.modified_time = this.modified_time != null ? moment(this.modified_time, DATE_TIME_FORMAT) : null;
        if (this.pc_tracker.id !== undefined) {
            this.subscribeToSaveResponse(this.pc_trackerService.update(this.pc_tracker));
        } else {
            this.subscribeToSaveResponse(this.pc_trackerService.create(this.pc_tracker));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPc_tracker>>) {
        result.subscribe((res: HttpResponse<IPc_tracker>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackElf_statusById(index: number, item: IElf_status) {
        return item.id;
    }

    trackConfigtypeById(index: number, item: IConfigtype) {
        return item.id;
    }
}
