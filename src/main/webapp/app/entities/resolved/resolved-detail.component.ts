import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResolved } from 'app/shared/model/resolved.model';

@Component({
    selector: 'jhi-resolved-detail',
    templateUrl: './resolved-detail.component.html'
})
export class ResolvedDetailComponent implements OnInit {
    resolved: IResolved;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ resolved }) => {
            this.resolved = resolved;
        });
    }

    previousState() {
        window.history.back();
    }
}
