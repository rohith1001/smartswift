import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDevpriority } from 'app/shared/model/devpriority.model';
import { DevpriorityService } from './devpriority.service';

@Component({
    selector: 'jhi-devpriority-update',
    templateUrl: './devpriority-update.component.html'
})
export class DevpriorityUpdateComponent implements OnInit {
    devpriority: IDevpriority;
    isSaving: boolean;

    constructor(private devpriorityService: DevpriorityService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ devpriority }) => {
            this.devpriority = devpriority;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.devpriority.id !== undefined) {
            this.subscribeToSaveResponse(this.devpriorityService.update(this.devpriority));
        } else {
            this.subscribeToSaveResponse(this.devpriorityService.create(this.devpriority));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDevpriority>>) {
        result.subscribe((res: HttpResponse<IDevpriority>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
