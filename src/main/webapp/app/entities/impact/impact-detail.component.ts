import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImpact } from 'app/shared/model/impact.model';

@Component({
    selector: 'jhi-impact-detail',
    templateUrl: './impact-detail.component.html'
})
export class ImpactDetailComponent implements OnInit {
    impact: IImpact;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ impact }) => {
            this.impact = impact;
        });
    }

    previousState() {
        window.history.back();
    }
}
