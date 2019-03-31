import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IBulkdefect } from 'app/shared/model/bulkdefect.model';

@Component({
    selector: 'jhi-bulkdefect-detail',
    templateUrl: './bulkdefect-detail.component.html'
})
export class BulkdefectDetailComponent implements OnInit {
    bulkdefect: IBulkdefect;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bulkdefect }) => {
            this.bulkdefect = bulkdefect;
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
