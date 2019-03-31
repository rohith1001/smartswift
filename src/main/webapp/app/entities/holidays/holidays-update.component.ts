import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IHolidays } from 'app/shared/model/holidays.model';
import { HolidaysService } from './holidays.service';

@Component({
    selector: 'jhi-holidays-update',
    templateUrl: './holidays-update.component.html'
})
export class HolidaysUpdateComponent implements OnInit {
    holidays: IHolidays;
    isSaving: boolean;
    holiday_dateDp: any;

    constructor(private holidaysService: HolidaysService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ holidays }) => {
            this.holidays = holidays;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.holidays.id !== undefined) {
            this.subscribeToSaveResponse(this.holidaysService.update(this.holidays));
        } else {
            this.subscribeToSaveResponse(this.holidaysService.create(this.holidays));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHolidays>>) {
        result.subscribe((res: HttpResponse<IHolidays>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
