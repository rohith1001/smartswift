import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPc_tracker } from 'app/shared/model/pc-tracker.model';

@Component({
    selector: 'jhi-pc-tracker-detail',
    templateUrl: './pc-tracker-detail.component.html'
})
export class Pc_trackerDetailComponent implements OnInit {
    pc_tracker: IPc_tracker;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pc_tracker }) => {
            this.pc_tracker = pc_tracker;
        });
    }

    previousState() {
        window.history.back();
    }
}
