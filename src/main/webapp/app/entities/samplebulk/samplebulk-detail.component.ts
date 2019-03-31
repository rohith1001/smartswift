import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISamplebulk } from 'app/shared/model/samplebulk.model';

@Component({
    selector: 'jhi-samplebulk-detail',
    templateUrl: './samplebulk-detail.component.html'
})
export class SamplebulkDetailComponent implements OnInit {
    samplebulk: ISamplebulk;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ samplebulk }) => {
            this.samplebulk = samplebulk;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
