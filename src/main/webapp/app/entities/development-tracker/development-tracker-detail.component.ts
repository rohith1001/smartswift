import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDevelopment_tracker } from 'app/shared/model/development-tracker.model';

@Component({
    selector: 'jhi-development-tracker-detail',
    templateUrl: './development-tracker-detail.component.html'
})
export class Development_trackerDetailComponent implements OnInit {
    development_tracker: IDevelopment_tracker;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ development_tracker }) => {
            this.development_tracker = development_tracker;
        });
    }

    previousState() {
        window.history.back();
    }
}
