import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStk } from 'app/shared/model/stk.model';

@Component({
    selector: 'jhi-stk-detail',
    templateUrl: './stk-detail.component.html',
    styles: [`
        .dtc{
            margin-bottom: 0.2rem !important;
            font-size: 13px !important;
            font-weight: 640;
        }
        .ddc{
            height: unset !important;
            font-family: inherit !important;
            font-size: 12px !important;
        }
    `]
})
export class StkDetailComponent implements OnInit {
    stk: IStk;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stk }) => {
            this.stk = stk;
        });
    }

    previousState() {
        window.history.back();
    }
}
