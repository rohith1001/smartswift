import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConfigtype } from 'app/shared/model/configtype.model';

@Component({
    selector: 'jhi-configtype-detail',
    templateUrl: './configtype-detail.component.html'
})
export class ConfigtypeDetailComponent implements OnInit {
    configtype: IConfigtype;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ configtype }) => {
            this.configtype = configtype;
        });
    }

    previousState() {
        window.history.back();
    }
}
