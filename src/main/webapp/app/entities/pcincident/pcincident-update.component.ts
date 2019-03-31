import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IPcincident } from 'app/shared/model/pcincident.model';
import { PcincidentService } from './pcincident.service';

@Component({
    selector: 'jhi-pcincident-update',
    templateUrl: './pcincident-update.component.html'
})
export class PcincidentUpdateComponent implements OnInit {
    pcincident: IPcincident;
    isSaving: boolean;
    date_recievedDp: any;
    release_dateDp: any;

    constructor(private pcincidentService: PcincidentService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pcincident }) => {
            this.pcincident = pcincident;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pcincident.id !== undefined) {
            this.subscribeToSaveResponse(this.pcincidentService.update(this.pcincident));
        } else {
            this.subscribeToSaveResponse(this.pcincidentService.create(this.pcincident));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPcincident>>) {
        result.subscribe((res: HttpResponse<IPcincident>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
