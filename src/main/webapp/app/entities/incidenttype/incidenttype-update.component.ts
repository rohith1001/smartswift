import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IIncidenttype } from 'app/shared/model/incidenttype.model';
import { IncidenttypeService } from './incidenttype.service';
import { ITicket } from 'app/shared/model/ticket.model';
import { TicketService } from 'app/entities/ticket';

@Component({
    selector: 'jhi-incidenttype-update',
    templateUrl: './incidenttype-update.component.html'
})
export class IncidenttypeUpdateComponent implements OnInit {
    incidenttype: IIncidenttype;
    isSaving: boolean;

    tickets: ITicket[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private incidenttypeService: IncidenttypeService,
        private ticketService: TicketService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ incidenttype }) => {
            this.incidenttype = incidenttype;
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
        if (this.incidenttype.id !== undefined) {
            this.subscribeToSaveResponse(this.incidenttypeService.update(this.incidenttype));
        } else {
            this.subscribeToSaveResponse(this.incidenttypeService.create(this.incidenttype));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IIncidenttype>>) {
        result.subscribe((res: HttpResponse<IIncidenttype>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
