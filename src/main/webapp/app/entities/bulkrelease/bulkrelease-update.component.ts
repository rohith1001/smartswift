import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IBulkrelease } from 'app/shared/model/bulkrelease.model';
import { BulkreleaseService } from './bulkrelease.service';

@Component({
    selector: 'jhi-bulkrelease-update',
    templateUrl: './bulkrelease-update.component.html'
})
export class BulkreleaseUpdateComponent implements OnInit {
    bulkrelease: IBulkrelease;
    isSaving: boolean;

    constructor(private dataUtils: JhiDataUtils, private bulkreleaseService: BulkreleaseService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
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

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bulkrelease.id !== undefined) {
            this.subscribeToSaveResponse(this.bulkreleaseService.update(this.bulkrelease));
        } else {
            this.subscribeToSaveResponse(this.bulkreleaseService.create(this.bulkrelease));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBulkrelease>>) {
        result.subscribe((res: HttpResponse<IBulkrelease>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
