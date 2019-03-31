import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { INewreport } from 'app/shared/model/newreport.model';

@Component({
    selector: 'jhi-newreport-detail',
    templateUrl: './newreport-detail.component.html'
})
export class NewreportDetailComponent implements OnInit {
    newreport: INewreport;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ newreport }) => {
            this.newreport = newreport;
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
