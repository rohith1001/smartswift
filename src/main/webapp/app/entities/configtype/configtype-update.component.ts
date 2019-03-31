import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IConfigtype } from 'app/shared/model/configtype.model';
import { ConfigtypeService } from './configtype.service';

@Component({
    selector: 'jhi-configtype-update',
    templateUrl: './configtype-update.component.html'
})
export class ConfigtypeUpdateComponent implements OnInit {
    configtype: IConfigtype;
    isSaving: boolean;

    constructor(private configtypeService: ConfigtypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ configtype }) => {
            this.configtype = configtype;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.configtype.id !== undefined) {
            this.subscribeToSaveResponse(this.configtypeService.update(this.configtype));
        } else {
            this.subscribeToSaveResponse(this.configtypeService.create(this.configtype));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IConfigtype>>) {
        result.subscribe((res: HttpResponse<IConfigtype>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
