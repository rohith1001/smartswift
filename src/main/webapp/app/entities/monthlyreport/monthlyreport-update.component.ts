import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IMonthlyreport } from 'app/shared/model/monthlyreport.model';
import { MonthlyreportService } from './monthlyreport.service';

@Component({
    selector: 'jhi-monthlyreport-update',
    templateUrl: './monthlyreport-update.component.html'
})
export class MonthlyreportUpdateComponent implements OnInit {
    monthlyreport: IMonthlyreport;
    isSaving: boolean;
    from_dateDp: any;
    to_dateDp: any;

    constructor(private monthlyreportService: MonthlyreportService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ monthlyreport }) => {
            this.monthlyreport = monthlyreport;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.monthlyreport.id !== undefined) {
            this.subscribeToSaveResponse(this.monthlyreportService.update(this.monthlyreport));
        } else {
            this.subscribeToSaveResponse(this.monthlyreportService.create(this.monthlyreport));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMonthlyreport>>) {
        result.subscribe((res: HttpResponse<IMonthlyreport>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
