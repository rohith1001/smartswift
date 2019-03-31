import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IState } from 'app/shared/model/state.model';
import { StateService } from './state.service';
import { ITicket } from 'app/shared/model/ticket.model';
import { TicketService } from 'app/entities/ticket';

@Component({
    selector: 'jhi-state-update',
    templateUrl: './state-update.component.html'
})
export class StateUpdateComponent implements OnInit {
    state: IState;
    isSaving: boolean;

    tickets: ITicket[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private stateService: StateService,
        private ticketService: TicketService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ state }) => {
            this.state = state;
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
        if (this.state.id !== undefined) {
            this.subscribeToSaveResponse(this.stateService.update(this.state));
        } else {
            this.subscribeToSaveResponse(this.stateService.create(this.state));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IState>>) {
        result.subscribe((res: HttpResponse<IState>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
