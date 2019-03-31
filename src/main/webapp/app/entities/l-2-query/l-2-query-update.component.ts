import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT, DATE_FROM, DATE_TO, DATE_TIME_REGEX } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IL2query } from 'app/shared/model/l-2-query.model';
import { L2queryService } from './l-2-query.service';
import { ITicket } from 'app/shared/model/ticket.model';
import { TicketService } from 'app/entities/ticket';
import { IState } from 'app/shared/model/state.model';
import { StateService } from 'app/entities/state';
import { IDomain } from 'app/shared/model/domain.model';
import { DomainService } from 'app/entities/domain';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';
import { IImpact } from 'app/shared/model/impact.model';
import { ImpactService } from 'app/entities/impact';
import { IPriority } from 'app/shared/model/priority.model';
import { PriorityService } from 'app/entities/priority';
import { IGroup } from 'app/shared/model/group.model';
import { GroupService } from 'app/entities/group';
import { IIssuetype } from 'app/shared/model/issuetype.model';
import { IssuetypeService } from 'app/entities/issuetype';
import { IIncidenttype } from 'app/shared/model/incidenttype.model';
import { IncidenttypeService } from 'app/entities/incidenttype';

@Component({
    selector: 'jhi-l-2-query-update',
    templateUrl: './l-2-query-update.component.html'
})
export class L2queryUpdateComponent implements OnInit {
    l2query: IL2query;
    isSaving: boolean;

    tickets: ITicket[];

    states: IState[];

    domains: IDomain[];

    categories: ICategory[];

    impacts: IImpact[];

    priorities: IPriority[];

    groups: IGroup[];

    issuetypes: IIssuetype[];

    incidenttypes: IIncidenttype[];
    stk_start_date: string;
    requested_date: string;
    responded_date: string;
    modified_time: string;
    rca_completed_date: string;
    nttdata_start_date: string;
    solution_found_date: string;
    passed_back_date: string;
    re_assigned_date: string;
    passed_back_date1: string;
    re_assigned_date1: string;
    passed_back_date2: string;
    re_assigned_date2: string;
    passed_back_date3: string;
    re_assigned_date3: string;
    passed_back_date4: string;
    re_assigned_date4: string;
    passed_back_date5: string;
    re_assigned_date5: string;
    closed_date: string;
    ter_date: string;
    rrt_start_date: string;
    rrt_completion_date: string;
    live_date: string;

    DF: string = DATE_FROM;
    DT: string = DATE_TO;
    DREGEX: string = DATE_TIME_REGEX;

    preventDefault(event){
        event.preventDefault();
    }

    constructor(
        private jhiAlertService: JhiAlertService,
        private l2queryService: L2queryService,
        private ticketService: TicketService,
        private stateService: StateService,
        private domainService: DomainService,
        private categoryService: CategoryService,
        private impactService: ImpactService,
        private priorityService: PriorityService,
        private groupService: GroupService,
        private issuetypeService: IssuetypeService,
        private incidenttypeService: IncidenttypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ l2query }) => {
            this.l2query = l2query;
            this.stk_start_date = this.l2query.stk_start_date != null ? this.l2query.stk_start_date.format(DATE_TIME_FORMAT) : null;
            this.requested_date = this.l2query.requested_date != null ? this.l2query.requested_date.format(DATE_TIME_FORMAT) : null;
            this.responded_date = this.l2query.responded_date != null ? this.l2query.responded_date.format(DATE_TIME_FORMAT) : null;
            this.modified_time = this.l2query.modified_time != null ? this.l2query.modified_time.format(DATE_TIME_FORMAT) : null;
            this.rca_completed_date =
                this.l2query.rca_completed_date != null ? this.l2query.rca_completed_date.format(DATE_TIME_FORMAT) : null;
            this.nttdata_start_date =
                this.l2query.nttdata_start_date != null ? this.l2query.nttdata_start_date.format(DATE_TIME_FORMAT) : null;
            this.solution_found_date =
                this.l2query.solution_found_date != null ? this.l2query.solution_found_date.format(DATE_TIME_FORMAT) : null;
            this.passed_back_date = this.l2query.passed_back_date != null ? this.l2query.passed_back_date.format(DATE_TIME_FORMAT) : null;
            this.re_assigned_date = this.l2query.re_assigned_date != null ? this.l2query.re_assigned_date.format(DATE_TIME_FORMAT) : null;
            this.passed_back_date1 =
                this.l2query.passed_back_date1 != null ? this.l2query.passed_back_date1.format(DATE_TIME_FORMAT) : null;
            this.re_assigned_date1 =
                this.l2query.re_assigned_date1 != null ? this.l2query.re_assigned_date1.format(DATE_TIME_FORMAT) : null;
            this.passed_back_date2 =
                this.l2query.passed_back_date2 != null ? this.l2query.passed_back_date2.format(DATE_TIME_FORMAT) : null;
            this.re_assigned_date2 =
                this.l2query.re_assigned_date2 != null ? this.l2query.re_assigned_date2.format(DATE_TIME_FORMAT) : null;
            this.passed_back_date3 =
                this.l2query.passed_back_date3 != null ? this.l2query.passed_back_date3.format(DATE_TIME_FORMAT) : null;
            this.re_assigned_date3 =
                this.l2query.re_assigned_date3 != null ? this.l2query.re_assigned_date3.format(DATE_TIME_FORMAT) : null;
            this.passed_back_date4 =
                this.l2query.passed_back_date4 != null ? this.l2query.passed_back_date4.format(DATE_TIME_FORMAT) : null;
            this.re_assigned_date4 =
                this.l2query.re_assigned_date4 != null ? this.l2query.re_assigned_date4.format(DATE_TIME_FORMAT) : null;
            this.passed_back_date5 =
                this.l2query.passed_back_date5 != null ? this.l2query.passed_back_date5.format(DATE_TIME_FORMAT) : null;
            this.re_assigned_date5 =
                this.l2query.re_assigned_date5 != null ? this.l2query.re_assigned_date5.format(DATE_TIME_FORMAT) : null;
            this.closed_date = this.l2query.closed_date != null ? this.l2query.closed_date.format(DATE_TIME_FORMAT) : null;
            this.ter_date = this.l2query.ter_date != null ? this.l2query.ter_date.format(DATE_TIME_FORMAT) : null;
            this.rrt_start_date = this.l2query.rrt_start_date != null ? this.l2query.rrt_start_date.format(DATE_TIME_FORMAT) : null;
            this.rrt_completion_date =
                this.l2query.rrt_completion_date != null ? this.l2query.rrt_completion_date.format(DATE_TIME_FORMAT) : null;
            this.live_date = this.l2query.live_date != null ? this.l2query.live_date.format(DATE_TIME_FORMAT) : null;
        });
        this.ticketService.query().subscribe(
            (res: HttpResponse<ITicket[]>) => {
                this.tickets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.stateService.query().subscribe(
            (res: HttpResponse<IState[]>) => {
                this.states = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.domainService.query().subscribe(
            (res: HttpResponse<IDomain[]>) => {
                this.domains = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.categoryService.query().subscribe(
            (res: HttpResponse<ICategory[]>) => {
                this.categories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.impactService.query().subscribe(
            (res: HttpResponse<IImpact[]>) => {
                this.impacts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.priorityService.query().subscribe(
            (res: HttpResponse<IPriority[]>) => {
                this.priorities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.groupService.query().subscribe(
            (res: HttpResponse<IGroup[]>) => {
                this.groups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.issuetypeService.query().subscribe(
            (res: HttpResponse<IIssuetype[]>) => {
                this.issuetypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.incidenttypeService.query().subscribe(
            (res: HttpResponse<IIncidenttype[]>) => {
                this.incidenttypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.l2query.stk_start_date = this.stk_start_date != null ? moment(this.stk_start_date, DATE_TIME_FORMAT) : null;
        this.l2query.requested_date = this.requested_date != null ? moment(this.requested_date, DATE_TIME_FORMAT) : null;
        this.l2query.responded_date = this.responded_date != null ? moment(this.responded_date, DATE_TIME_FORMAT) : null;
        this.l2query.modified_time = this.modified_time != null ? moment(this.modified_time, DATE_TIME_FORMAT) : null;
        this.l2query.rca_completed_date = this.rca_completed_date != null ? moment(this.rca_completed_date, DATE_TIME_FORMAT) : null;
        this.l2query.nttdata_start_date = this.nttdata_start_date != null ? moment(this.nttdata_start_date, DATE_TIME_FORMAT) : null;
        this.l2query.solution_found_date = this.solution_found_date != null ? moment(this.solution_found_date, DATE_TIME_FORMAT) : null;
        this.l2query.passed_back_date = this.passed_back_date != null ? moment(this.passed_back_date, DATE_TIME_FORMAT) : null;
        this.l2query.re_assigned_date = this.re_assigned_date != null ? moment(this.re_assigned_date, DATE_TIME_FORMAT) : null;
        this.l2query.passed_back_date1 = this.passed_back_date1 != null ? moment(this.passed_back_date1, DATE_TIME_FORMAT) : null;
        this.l2query.re_assigned_date1 = this.re_assigned_date1 != null ? moment(this.re_assigned_date1, DATE_TIME_FORMAT) : null;
        this.l2query.passed_back_date2 = this.passed_back_date2 != null ? moment(this.passed_back_date2, DATE_TIME_FORMAT) : null;
        this.l2query.re_assigned_date2 = this.re_assigned_date2 != null ? moment(this.re_assigned_date2, DATE_TIME_FORMAT) : null;
        this.l2query.passed_back_date3 = this.passed_back_date3 != null ? moment(this.passed_back_date3, DATE_TIME_FORMAT) : null;
        this.l2query.re_assigned_date3 = this.re_assigned_date3 != null ? moment(this.re_assigned_date3, DATE_TIME_FORMAT) : null;
        this.l2query.passed_back_date4 = this.passed_back_date4 != null ? moment(this.passed_back_date4, DATE_TIME_FORMAT) : null;
        this.l2query.re_assigned_date4 = this.re_assigned_date4 != null ? moment(this.re_assigned_date4, DATE_TIME_FORMAT) : null;
        this.l2query.passed_back_date5 = this.passed_back_date5 != null ? moment(this.passed_back_date5, DATE_TIME_FORMAT) : null;
        this.l2query.re_assigned_date5 = this.re_assigned_date5 != null ? moment(this.re_assigned_date5, DATE_TIME_FORMAT) : null;
        this.l2query.closed_date = this.closed_date != null ? moment(this.closed_date, DATE_TIME_FORMAT) : null;
        this.l2query.ter_date = this.ter_date != null ? moment(this.ter_date, DATE_TIME_FORMAT) : null;
        this.l2query.rrt_start_date = this.rrt_start_date != null ? moment(this.rrt_start_date, DATE_TIME_FORMAT) : null;
        this.l2query.rrt_completion_date = this.rrt_completion_date != null ? moment(this.rrt_completion_date, DATE_TIME_FORMAT) : null;
        this.l2query.live_date = this.live_date != null ? moment(this.live_date, DATE_TIME_FORMAT) : null;
        if (this.l2query.id !== undefined) {
            this.subscribeToSaveResponse(this.l2queryService.update(this.l2query));
        } else {
            this.subscribeToSaveResponse(this.l2queryService.create(this.l2query));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IL2query>>) {
        result.subscribe((res: HttpResponse<IL2query>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackStateById(index: number, item: IState) {
        return item.id;
    }

    trackDomainById(index: number, item: IDomain) {
        return item.id;
    }

    trackCategoryById(index: number, item: ICategory) {
        return item.id;
    }

    trackImpactById(index: number, item: IImpact) {
        return item.id;
    }

    trackPriorityById(index: number, item: IPriority) {
        return item.id;
    }

    trackGroupById(index: number, item: IGroup) {
        return item.id;
    }

    trackIssuetypeById(index: number, item: IIssuetype) {
        return item.id;
    }

    trackIncidenttypeById(index: number, item: IIncidenttype) {
        return item.id;
    }
}
