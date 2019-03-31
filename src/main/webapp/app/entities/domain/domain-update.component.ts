import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDomain } from 'app/shared/model/domain.model';
import { DomainService } from './domain.service';
import { ITicket } from 'app/shared/model/ticket.model';
import { TicketService } from 'app/entities/ticket';

@Component({
    selector: 'jhi-domain-update',
    templateUrl: './domain-update.component.html'
})
export class DomainUpdateComponent implements OnInit {
    domain: IDomain;
    isSaving: boolean;

    tickets: ITicket[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private domainService: DomainService,
        private ticketService: TicketService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ domain }) => {
            this.domain = domain;
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
        if (this.domain.id !== undefined) {
            this.subscribeToSaveResponse(this.domainService.update(this.domain));
        } else {
            this.subscribeToSaveResponse(this.domainService.create(this.domain));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDomain>>) {
        result.subscribe((res: HttpResponse<IDomain>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
