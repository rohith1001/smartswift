import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPcincident } from 'app/shared/model/pcincident.model';

@Component({
    selector: 'jhi-pcincident-detail',
    templateUrl: './pcincident-detail.component.html'
})
export class PcincidentDetailComponent implements OnInit {
    pcincident: IPcincident;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pcincident }) => {
            this.pcincident = pcincident;
        });
    }

    previousState() {
        window.history.back();
    }
}
