import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { INewreport } from 'app/shared/model/newreport.model';
import { NewreportService } from './newreport.service';

@Component({
    selector: 'jhi-newreport-update',
    templateUrl: './newreport-update.component.html'
})
export class NewreportUpdateComponent implements OnInit {
    newreport: INewreport;
    isSaving: boolean;

    constructor(private dataUtils: JhiDataUtils, private newreportService: NewreportService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
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

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.newreport.id !== undefined) {
            this.subscribeToSaveResponse(this.newreportService.update(this.newreport));
        } else {
            this.subscribeToSaveResponse(this.newreportService.create(this.newreport));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<INewreport>>) {
        result.subscribe((res: HttpResponse<INewreport>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
