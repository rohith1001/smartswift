import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IGroup } from 'app/shared/model/group.model';
import { GroupService } from './group.service';
import { IDomain } from 'app/shared/model/domain.model';
import { DomainService } from 'app/entities/domain';
import { ITicket } from 'app/shared/model/ticket.model';
import { TicketService } from 'app/entities/ticket';

@Component({
    selector: 'jhi-group-update',
    templateUrl: './group-update.component.html'
})
export class GroupUpdateComponent implements OnInit {
    group: IGroup;
    isSaving: boolean;

    domains: IDomain[];

    tickets: ITicket[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private groupService: GroupService,
        private domainService: DomainService,
        private ticketService: TicketService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ group }) => {
            this.group = group;
        });
        this.domainService.query().subscribe(
            (res: HttpResponse<IDomain[]>) => {
                this.domains = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.group.id !== undefined) {
            this.subscribeToSaveResponse(this.groupService.update(this.group));
        } else {
            this.subscribeToSaveResponse(this.groupService.create(this.group));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGroup>>) {
        result.subscribe((res: HttpResponse<IGroup>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDomainById(index: number, item: IDomain) {
        return item.id;
    }

    trackTicketById(index: number, item: ITicket) {
        return item.id;
    }
}
