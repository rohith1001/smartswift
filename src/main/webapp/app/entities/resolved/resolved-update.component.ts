import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IResolved } from 'app/shared/model/resolved.model';
import { ResolvedService } from './resolved.service';

@Component({
    selector: 'jhi-resolved-update',
    templateUrl: './resolved-update.component.html'
})
export class ResolvedUpdateComponent implements OnInit {
    resolved: IResolved;
    isSaving: boolean;

    constructor(private resolvedService: ResolvedService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ resolved }) => {
            this.resolved = resolved;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.resolved.id !== undefined) {
            this.subscribeToSaveResponse(this.resolvedService.update(this.resolved));
        } else {
            this.subscribeToSaveResponse(this.resolvedService.create(this.resolved));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IResolved>>) {
        result.subscribe((res: HttpResponse<IResolved>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
