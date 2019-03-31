import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHld } from 'app/shared/model/hld.model';

@Component({
    selector: 'jhi-hld-detail',
    templateUrl: './hld-detail.component.html'
})
export class HldDetailComponent implements OnInit {
    hld: IHld;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hld }) => {
            this.hld = hld;
        });
    }

    previousState() {
        window.history.back();
    }
}
