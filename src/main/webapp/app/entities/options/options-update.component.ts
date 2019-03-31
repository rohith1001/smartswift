import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IOptions } from 'app/shared/model/options.model';
import { OptionsService } from './options.service';

@Component({
    selector: 'jhi-options-update',
    templateUrl: './options-update.component.html'
})
export class OptionsUpdateComponent implements OnInit {
    options: IOptions;
    isSaving: boolean;

    constructor(private optionsService: OptionsService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ options }) => {
            this.options = options;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.options.id !== undefined) {
            this.subscribeToSaveResponse(this.optionsService.update(this.options));
        } else {
            this.subscribeToSaveResponse(this.optionsService.create(this.options));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOptions>>) {
        result.subscribe((res: HttpResponse<IOptions>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
