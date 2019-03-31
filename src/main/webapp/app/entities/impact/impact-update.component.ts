import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IImpact } from 'app/shared/model/impact.model';
import { ImpactService } from './impact.service';

@Component({
    selector: 'jhi-impact-update',
    templateUrl: './impact-update.component.html'
})
export class ImpactUpdateComponent implements OnInit {
    impact: IImpact;
    isSaving: boolean;

    constructor(private impactService: ImpactService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ impact }) => {
            this.impact = impact;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.impact.id !== undefined) {
            this.subscribeToSaveResponse(this.impactService.update(this.impact));
        } else {
            this.subscribeToSaveResponse(this.impactService.create(this.impact));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IImpact>>) {
        result.subscribe((res: HttpResponse<IImpact>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
