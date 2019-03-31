import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIia } from 'app/shared/model/iia.model';

@Component({
    selector: 'jhi-iia-detail',
    templateUrl: './iia-detail.component.html'
})
export class IiaDetailComponent implements OnInit {
    iia: IIia;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ iia }) => {
            this.iia = iia;
        });
    }

    previousState() {
        window.history.back();
    }
}
