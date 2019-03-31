import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHolidays } from 'app/shared/model/holidays.model';

@Component({
    selector: 'jhi-holidays-detail',
    templateUrl: './holidays-detail.component.html'
})
export class HolidaysDetailComponent implements OnInit {
    holidays: IHolidays;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ holidays }) => {
            this.holidays = holidays;
        });
    }

    previousState() {
        window.history.back();
    }
}
