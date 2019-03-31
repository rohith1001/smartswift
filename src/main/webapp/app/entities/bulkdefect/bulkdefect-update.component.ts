import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IBulkdefect } from 'app/shared/model/bulkdefect.model';
import { BulkdefectService } from './bulkdefect.service';

@Component({
    selector: 'jhi-bulkdefect-update',
    templateUrl: './bulkdefect-update.component.html'
})
export class BulkdefectUpdateComponent implements OnInit {
    bulkdefect: IBulkdefect;
    isSaving: boolean;

    constructor(private dataUtils: JhiDataUtils, private bulkdefectService: BulkdefectService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
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

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bulkdefect.id !== undefined) {
            this.subscribeToSaveResponse(this.bulkdefectService.update(this.bulkdefect));
        } else {
            this.subscribeToSaveResponse(this.bulkdefectService.create(this.bulkdefect));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBulkdefect>>) {
        result.subscribe((res: HttpResponse<IBulkdefect>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
