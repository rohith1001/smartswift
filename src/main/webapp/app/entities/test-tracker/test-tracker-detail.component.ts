import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITest_tracker } from 'app/shared/model/test-tracker.model';

@Component({
    selector: 'jhi-test-tracker-detail',
    templateUrl: './test-tracker-detail.component.html'
})
export class Test_trackerDetailComponent implements OnInit {
    test_tracker: ITest_tracker;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ test_tracker }) => {
            this.test_tracker = test_tracker;
        });
    }

    previousState() {
        window.history.back();
    }
}
