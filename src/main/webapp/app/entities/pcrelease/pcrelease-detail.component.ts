import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPcrelease } from 'app/shared/model/pcrelease.model';

@Component({
    selector: 'jhi-pcrelease-detail',
    templateUrl: './pcrelease-detail.component.html'
})
export class PcreleaseDetailComponent implements OnInit {
    pcrelease: IPcrelease;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pcrelease }) => {
            this.pcrelease = pcrelease;
        });
    }

    previousState() {
        window.history.back();
    }
}
