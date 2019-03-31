import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT, DATE_FROM, DATE_TO, DATE_TIME_REGEX } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPctracker } from 'app/shared/model/pctracker.model';
import { PctrackerService } from './pctracker.service';
import { IConfigtype } from 'app/shared/model/configtype.model';
import { ConfigtypeService } from 'app/entities/configtype';
import { IElf_status } from 'app/shared/model/elf-status.model';
import { Elf_statusService } from 'app/entities/elf-status';

@Component({
    selector: 'jhi-pctracker-update',
    templateUrl: './pctracker-update.component.html'
})
export class PctrackerUpdateComponent implements OnInit {
    pctracker: IPctracker;
    isSaving: boolean;

    configtypes: IConfigtype[];

    elf_statuses: IElf_status[];
    date_received: string;
    iia_deliver_date_planned: string;
    iia_deliver_date_actual: string;
    dcd_deliver_date_planned: string;
    dcd_deliver_date_actual: string;
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
    
    DF: string = DATE_FROM;
    DT: string = DATE_TO;
    DREGEX: string = DATE_TIME_REGEX;

    preventDefault(event){
        event.preventDefault();
    }

    constructor(
        private jhiAlertService: JhiAlertService,
        private pctrackerService: PctrackerService,
        private configtypeService: ConfigtypeService,
        private elf_statusService: Elf_statusService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pctracker }) => {
            this.pctracker = pctracker;
            this.date_received = this.pctracker.date_received != null ? this.pctracker.date_received.format(DATE_TIME_FORMAT) : null;
            this.iia_deliver_date_planned =
                this.pctracker.iia_deliver_date_planned != null ? this.pctracker.iia_deliver_date_planned.format(DATE_TIME_FORMAT) : null;
            this.iia_deliver_date_actual =
                this.pctracker.iia_deliver_date_actual != null ? this.pctracker.iia_deliver_date_actual.format(DATE_TIME_FORMAT) : null;
            this.dcd_deliver_date_planned =
                this.pctracker.dcd_deliver_date_planned != null ? this.pctracker.dcd_deliver_date_planned.format(DATE_TIME_FORMAT) : null;
            this.dcd_deliver_date_actual =
                this.pctracker.dcd_deliver_date_actual != null ? this.pctracker.dcd_deliver_date_actual.format(DATE_TIME_FORMAT) : null;
            this.wr_acknowledge_date_planned =
                this.pctracker.wr_acknowledge_date_planned != null
                    ? this.pctracker.wr_acknowledge_date_planned.format(DATE_TIME_FORMAT)
                    : null;
            this.wr_acknowledge_date_actual =
                this.pctracker.wr_acknowledge_date_actual != null
                    ? this.pctracker.wr_acknowledge_date_actual.format(DATE_TIME_FORMAT)
                    : null;
            this.wr_costready_date_planned =
                this.pctracker.wr_costready_date_planned != null ? this.pctracker.wr_costready_date_planned.format(DATE_TIME_FORMAT) : null;
            this.wr_costready_date_actual =
                this.pctracker.wr_costready_date_actual != null ? this.pctracker.wr_costready_date_actual.format(DATE_TIME_FORMAT) : null;
            this.hlcd_delivery_date_planned =
                this.pctracker.hlcd_delivery_date_planned != null
                    ? this.pctracker.hlcd_delivery_date_planned.format(DATE_TIME_FORMAT)
                    : null;
            this.hlcd_delivery_date_actual =
                this.pctracker.hlcd_delivery_date_actual != null ? this.pctracker.hlcd_delivery_date_actual.format(DATE_TIME_FORMAT) : null;
            this.test_ready_date_planned =
                this.pctracker.test_ready_date_planned != null ? this.pctracker.test_ready_date_planned.format(DATE_TIME_FORMAT) : null;
            this.test_ready_date_actual =
                this.pctracker.test_ready_date_actual != null ? this.pctracker.test_ready_date_actual.format(DATE_TIME_FORMAT) : null;
            this.launch_date_planned =
                this.pctracker.launch_date_planned != null ? this.pctracker.launch_date_planned.format(DATE_TIME_FORMAT) : null;
            this.launch_date_actual =
                this.pctracker.launch_date_actual != null ? this.pctracker.launch_date_actual.format(DATE_TIME_FORMAT) : null;
            this.delivery_date_planned =
                this.pctracker.delivery_date_planned != null ? this.pctracker.delivery_date_planned.format(DATE_TIME_FORMAT) : null;
            this.delivery_date_actual =
                this.pctracker.delivery_date_actual != null ? this.pctracker.delivery_date_actual.format(DATE_TIME_FORMAT) : null;
            this.modified_time = this.pctracker.modified_time != null ? this.pctracker.modified_time.format(DATE_TIME_FORMAT) : null;
        });
        this.configtypeService.query().subscribe(
            (res: HttpResponse<IConfigtype[]>) => {
                this.configtypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.elf_statusService.query().subscribe(
            (res: HttpResponse<IElf_status[]>) => {
                this.elf_statuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.pctracker.date_received = this.date_received != null ? moment(this.date_received, DATE_TIME_FORMAT) : null;
        this.pctracker.iia_deliver_date_planned =
            this.iia_deliver_date_planned != null ? moment(this.iia_deliver_date_planned, DATE_TIME_FORMAT) : null;
        this.pctracker.iia_deliver_date_actual =
            this.iia_deliver_date_actual != null ? moment(this.iia_deliver_date_actual, DATE_TIME_FORMAT) : null;
        this.pctracker.dcd_deliver_date_planned =
            this.dcd_deliver_date_planned != null ? moment(this.dcd_deliver_date_planned, DATE_TIME_FORMAT) : null;
        this.pctracker.dcd_deliver_date_actual =
            this.dcd_deliver_date_actual != null ? moment(this.dcd_deliver_date_actual, DATE_TIME_FORMAT) : null;
        this.pctracker.wr_acknowledge_date_planned =
            this.wr_acknowledge_date_planned != null ? moment(this.wr_acknowledge_date_planned, DATE_TIME_FORMAT) : null;
        this.pctracker.wr_acknowledge_date_actual =
            this.wr_acknowledge_date_actual != null ? moment(this.wr_acknowledge_date_actual, DATE_TIME_FORMAT) : null;
        this.pctracker.wr_costready_date_planned =
            this.wr_costready_date_planned != null ? moment(this.wr_costready_date_planned, DATE_TIME_FORMAT) : null;
        this.pctracker.wr_costready_date_actual =
            this.wr_costready_date_actual != null ? moment(this.wr_costready_date_actual, DATE_TIME_FORMAT) : null;
        this.pctracker.hlcd_delivery_date_planned =
            this.hlcd_delivery_date_planned != null ? moment(this.hlcd_delivery_date_planned, DATE_TIME_FORMAT) : null;
        this.pctracker.hlcd_delivery_date_actual =
            this.hlcd_delivery_date_actual != null ? moment(this.hlcd_delivery_date_actual, DATE_TIME_FORMAT) : null;
        this.pctracker.test_ready_date_planned =
            this.test_ready_date_planned != null ? moment(this.test_ready_date_planned, DATE_TIME_FORMAT) : null;
        this.pctracker.test_ready_date_actual =
            this.test_ready_date_actual != null ? moment(this.test_ready_date_actual, DATE_TIME_FORMAT) : null;
        this.pctracker.launch_date_planned = this.launch_date_planned != null ? moment(this.launch_date_planned, DATE_TIME_FORMAT) : null;
        this.pctracker.launch_date_actual = this.launch_date_actual != null ? moment(this.launch_date_actual, DATE_TIME_FORMAT) : null;
        this.pctracker.delivery_date_planned =
            this.delivery_date_planned != null ? moment(this.delivery_date_planned, DATE_TIME_FORMAT) : null;
        this.pctracker.delivery_date_actual =
            this.delivery_date_actual != null ? moment(this.delivery_date_actual, DATE_TIME_FORMAT) : null;
        this.pctracker.modified_time = this.modified_time != null ? moment(this.modified_time, DATE_TIME_FORMAT) : null;
        if (this.pctracker.id !== undefined) {
            this.subscribeToSaveResponse(this.pctrackerService.update(this.pctracker));
        } else {
            this.subscribeToSaveResponse(this.pctrackerService.create(this.pctracker));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPctracker>>) {
        result.subscribe((res: HttpResponse<IPctracker>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackConfigtypeById(index: number, item: IConfigtype) {
        return item.id;
    }

    trackElf_statusById(index: number, item: IElf_status) {
        return item.id;
    }
}
