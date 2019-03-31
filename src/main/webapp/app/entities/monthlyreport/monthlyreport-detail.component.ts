import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMonthlyreport } from 'app/shared/model/monthlyreport.model';

@Component({
    selector: 'jhi-monthlyreport-detail',
    templateUrl: './monthlyreport-detail.component.html'
})
export class MonthlyreportDetailComponent implements OnInit {
    monthlyreport: IMonthlyreport;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ monthlyreport }) => {
            this.monthlyreport = monthlyreport;
        });
    }

    previousState() {
        window.history.back();
    }
}
