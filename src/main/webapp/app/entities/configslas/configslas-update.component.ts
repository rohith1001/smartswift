import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IConfigslas } from 'app/shared/model/configslas.model';
import { ConfigslasService } from './configslas.service';

@Component({
    selector: 'jhi-configslas-update',
    templateUrl: './configslas-update.component.html'
})
export class ConfigslasUpdateComponent implements OnInit {
    configslas: IConfigslas;
    isSaving: boolean;

    constructor(private configslasService: ConfigslasService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ configslas }) => {
            this.configslas = configslas;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.configslas.id !== undefined) {
            this.subscribeToSaveResponse(this.configslasService.update(this.configslas));
        } else {
            this.subscribeToSaveResponse(this.configslasService.create(this.configslas));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IConfigslas>>) {
        result.subscribe((res: HttpResponse<IConfigslas>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
