import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPcdefect } from 'app/shared/model/pcdefect.model';

@Component({
    selector: 'jhi-pcdefect-detail',
    templateUrl: './pcdefect-detail.component.html'
})
export class PcdefectDetailComponent implements OnInit {
    pcdefect: IPcdefect;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pcdefect }) => {
            this.pcdefect = pcdefect;
        });
    }

    previousState() {
        window.history.back();
    }
}
