import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConfigslas } from 'app/shared/model/configslas.model';

@Component({
    selector: 'jhi-configslas-detail',
    templateUrl: './configslas-detail.component.html'
})
export class ConfigslasDetailComponent implements OnInit {
    configslas: IConfigslas;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ configslas }) => {
            this.configslas = configslas;
        });
    }

    previousState() {
        window.history.back();
    }
}
