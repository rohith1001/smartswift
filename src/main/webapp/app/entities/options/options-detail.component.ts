import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOptions } from 'app/shared/model/options.model';

@Component({
    selector: 'jhi-options-detail',
    templateUrl: './options-detail.component.html'
})
export class OptionsDetailComponent implements OnInit {
    options: IOptions;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ options }) => {
            this.options = options;
        });
    }

    previousState() {
        window.history.back();
    }
}
