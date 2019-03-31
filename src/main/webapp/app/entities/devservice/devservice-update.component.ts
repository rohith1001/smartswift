import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDevservice } from 'app/shared/model/devservice.model';
import { DevserviceService } from './devservice.service';

@Component({
    selector: 'jhi-devservice-update',
    templateUrl: './devservice-update.component.html'
})
export class DevserviceUpdateComponent implements OnInit {
    devservice: IDevservice;
    isSaving: boolean;

    constructor(private devserviceService: DevserviceService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ devservice }) => {
            this.devservice = devservice;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.devservice.id !== undefined) {
            this.subscribeToSaveResponse(this.devserviceService.update(this.devservice));
        } else {
            this.subscribeToSaveResponse(this.devserviceService.create(this.devservice));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDevservice>>) {
        result.subscribe((res: HttpResponse<IDevservice>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
