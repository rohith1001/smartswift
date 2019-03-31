import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IBulkincident } from 'app/shared/model/bulkincident.model';
import { BulkincidentService } from './bulkincident.service';

@Component({
    selector: 'jhi-bulkincident-update',
    templateUrl: './bulkincident-update.component.html'
})
export class BulkincidentUpdateComponent implements OnInit {
    bulkincident: IBulkincident;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private bulkincidentService: BulkincidentService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
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

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bulkincident.id !== undefined) {
            this.subscribeToSaveResponse(this.bulkincidentService.update(this.bulkincident));
        } else {
            this.subscribeToSaveResponse(this.bulkincidentService.create(this.bulkincident));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBulkincident>>) {
        result.subscribe((res: HttpResponse<IBulkincident>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
