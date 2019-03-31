import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIncidenttype } from 'app/shared/model/incidenttype.model';

@Component({
    selector: 'jhi-incidenttype-detail',
    templateUrl: './incidenttype-detail.component.html'
})
export class IncidenttypeDetailComponent implements OnInit {
    incidenttype: IIncidenttype;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ incidenttype }) => {
            this.incidenttype = incidenttype;
        });
    }

    previousState() {
        window.history.back();
    }
}
