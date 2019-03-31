import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IBulkrelease } from 'app/shared/model/bulkrelease.model';

@Component({
    selector: 'jhi-bulkrelease-detail',
    templateUrl: './bulkrelease-detail.component.html'
})
export class BulkreleaseDetailComponent implements OnInit {
    bulkrelease: IBulkrelease;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bulkrelease }) => {
            this.bulkrelease = bulkrelease;
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
