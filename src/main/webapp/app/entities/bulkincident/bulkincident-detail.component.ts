import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IBulkincident } from 'app/shared/model/bulkincident.model';

@Component({
    selector: 'jhi-bulkincident-detail',
    templateUrl: './bulkincident-detail.component.html'
})
export class BulkincidentDetailComponent implements OnInit {
    bulkincident: IBulkincident;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bulkincident }) => {
            this.bulkincident = bulkincident;
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
