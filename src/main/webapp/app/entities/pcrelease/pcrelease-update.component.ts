import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IPcrelease } from 'app/shared/model/pcrelease.model';
import { PcreleaseService } from './pcrelease.service';

@Component({
    selector: 'jhi-pcrelease-update',
    templateUrl: './pcrelease-update.component.html'
})
export class PcreleaseUpdateComponent implements OnInit {
    pcrelease: IPcrelease;
    isSaving: boolean;
    release_dateDp: any;

    constructor(private pcreleaseService: PcreleaseService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pcrelease }) => {
            this.pcrelease = pcrelease;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pcrelease.id !== undefined) {
            this.subscribeToSaveResponse(this.pcreleaseService.update(this.pcrelease));
        } else {
            this.subscribeToSaveResponse(this.pcreleaseService.create(this.pcrelease));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPcrelease>>) {
        result.subscribe((res: HttpResponse<IPcrelease>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
