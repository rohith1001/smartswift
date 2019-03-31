import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IPriority } from 'app/shared/model/priority.model';
import { PriorityService } from './priority.service';
import { ITicket } from 'app/shared/model/ticket.model';
import { TicketService } from 'app/entities/ticket';

@Component({
    selector: 'jhi-priority-update',
    templateUrl: './priority-update.component.html'
})
export class PriorityUpdateComponent implements OnInit {
    priority: IPriority;
    isSaving: boolean;

    tickets: ITicket[];
    ack_timeDp: any;
    restore_timeDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private priorityService: PriorityService,
        private ticketService: TicketService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ priority }) => {
            this.priority = priority;
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
        if (this.priority.id !== undefined) {
            this.subscribeToSaveResponse(this.priorityService.update(this.priority));
        } else {
            this.subscribeToSaveResponse(this.priorityService.create(this.priority));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPriority>>) {
        result.subscribe((res: HttpResponse<IPriority>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
