import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDevservice } from 'app/shared/model/devservice.model';

@Component({
    selector: 'jhi-devservice-detail',
    templateUrl: './devservice-detail.component.html'
})
export class DevserviceDetailComponent implements OnInit {
    devservice: IDevservice;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ devservice }) => {
            this.devservice = devservice;
        });
    }

    previousState() {
        window.history.back();
    }
}
