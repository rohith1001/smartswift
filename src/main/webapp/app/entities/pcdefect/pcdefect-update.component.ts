import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IPcdefect } from 'app/shared/model/pcdefect.model';
import { PcdefectService } from './pcdefect.service';

@Component({
    selector: 'jhi-pcdefect-update',
    templateUrl: './pcdefect-update.component.html'
})
export class PcdefectUpdateComponent implements OnInit {
    pcdefect: IPcdefect;
    isSaving: boolean;
    date_raisedDp: any;
    date_closedDp: any;
    release_dateDp: any;

    constructor(private pcdefectService: PcdefectService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pcdefect }) => {
            this.pcdefect = pcdefect;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pcdefect.id !== undefined) {
            this.subscribeToSaveResponse(this.pcdefectService.update(this.pcdefect));
        } else {
            this.subscribeToSaveResponse(this.pcdefectService.create(this.pcdefect));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPcdefect>>) {
        result.subscribe((res: HttpResponse<IPcdefect>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
