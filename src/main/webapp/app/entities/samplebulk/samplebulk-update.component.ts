import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ISamplebulk } from 'app/shared/model/samplebulk.model';
import { SamplebulkService } from './samplebulk.service';

@Component({
    selector: 'jhi-samplebulk-update',
    templateUrl: './samplebulk-update.component.html'
})
export class SamplebulkUpdateComponent implements OnInit {
    samplebulk: ISamplebulk;
    isSaving: boolean;

    constructor(private dataUtils: JhiDataUtils, private samplebulkService: SamplebulkService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
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

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.samplebulk.id !== undefined) {
            this.subscribeToSaveResponse(this.samplebulkService.update(this.samplebulk));
        } else {
            this.subscribeToSaveResponse(this.samplebulkService.create(this.samplebulk));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISamplebulk>>) {
        result.subscribe((res: HttpResponse<ISamplebulk>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
