import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIssuetype } from 'app/shared/model/issuetype.model';

@Component({
    selector: 'jhi-issuetype-detail',
    templateUrl: './issuetype-detail.component.html'
})
export class IssuetypeDetailComponent implements OnInit {
    issuetype: IIssuetype;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ issuetype }) => {
            this.issuetype = issuetype;
        });
    }

    previousState() {
        window.history.back();
    }
}
