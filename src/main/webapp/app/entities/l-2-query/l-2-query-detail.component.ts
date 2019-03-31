import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IL2query } from 'app/shared/model/l-2-query.model';

@Component({
    selector: 'jhi-l-2-query-detail',
    templateUrl: './l-2-query-detail.component.html',
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
export class L2queryDetailComponent implements OnInit {
    l2query: IL2query;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ l2query }) => {
            this.l2query = l2query;
        });
    }

    previousState() {
        window.history.back();
    }
}
