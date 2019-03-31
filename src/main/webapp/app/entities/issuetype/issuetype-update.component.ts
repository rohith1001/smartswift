import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IIssuetype } from 'app/shared/model/issuetype.model';
import { IssuetypeService } from './issuetype.service';
import { ITicket } from 'app/shared/model/ticket.model';
import { TicketService } from 'app/entities/ticket';

@Component({
    selector: 'jhi-issuetype-update',
    templateUrl: './issuetype-update.component.html'
})
export class IssuetypeUpdateComponent implements OnInit {
    issuetype: IIssuetype;
    isSaving: boolean;

    tickets: ITicket[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private issuetypeService: IssuetypeService,
        private ticketService: TicketService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ issuetype }) => {
            this.issuetype = issuetype;
        });
        this.ticketService.query().subscribe(
            (res: HttpResponse<ITicket[]>) => {
                this.tickets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.issuetype.id !== undefined) {
            this.subscribeToSaveResponse(this.issuetypeService.update(this.issuetype));
        } else {
            this.subscribeToSaveResponse(this.issuetypeService.create(this.issuetype));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IIssuetype>>) {
        result.subscribe((res: HttpResponse<IIssuetype>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackTicketById(index: number, item: ITicket) {
        return item.id;
    }
}
