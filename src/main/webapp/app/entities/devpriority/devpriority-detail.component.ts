import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDevpriority } from 'app/shared/model/devpriority.model';

@Component({
    selector: 'jhi-devpriority-detail',
    templateUrl: './devpriority-detail.component.html'
})
export class DevpriorityDetailComponent implements OnInit {
    devpriority: IDevpriority;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ devpriority }) => {
            this.devpriority = devpriority;
        });
    }

    previousState() {
        window.history.back();
    }
}
