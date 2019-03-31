import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT, DATE_FROM, DATE_TO, DATE_TIME_REGEX } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IStk } from 'app/shared/model/stk.model';
import { StkService } from './stk.service';
import { ITicket } from 'app/shared/model/ticket.model';
import { TicketService } from 'app/entities/ticket';
import { IState } from 'app/shared/model/state.model';
import { StateService } from 'app/entities/state';
import { IDomain } from 'app/shared/model/domain.model';
import { DomainService } from 'app/entities/domain';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';
import { IPriority } from 'app/shared/model/priority.model';
import { PriorityService } from 'app/entities/priority';
import { IGroup } from 'app/shared/model/group.model';
import { GroupService } from 'app/entities/group';
import { IIssuetype } from 'app/shared/model/issuetype.model';
import { IssuetypeService } from 'app/entities/issuetype';
import { IIncidenttype } from 'app/shared/model/incidenttype.model';
import { IncidenttypeService } from 'app/entities/incidenttype';
import { IImpact } from 'app/shared/model/impact.model';
import { ImpactService } from 'app/entities/impact';

@Component({
    selector: 'jhi-stk-update',
    templateUrl: './stk-update.component.html'
})
export class StkUpdateComponent implements OnInit {
    stk: IStk;
    isSaving: boolean;

    tickets: ITicket[];

    states: IState[];

    domains: IDomain[];

    categories: ICategory[];

    priorities: IPriority[];

    groups: IGroup[];

    issuetypes: IIssuetype[];

    incidenttypes: IIncidenttype[];

    impacts: IImpact[];
    stk_start_date: string;
    requested_date: string;
    responded_time: string;
    modified_time: string;
    nttdata_start_date: string;
    rca_completed_date: string;
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
    finaldate_rca: string;
    finaldate_solution: string;

    DF: string = DATE_FROM;
    DT: string = DATE_TO;
    DREGEX: string = DATE_TIME_REGEX;

    preventDefault(event){
        event.preventDefault();
    }

    constructor(
        private jhiAlertService: JhiAlertService,
        private stkService: StkService,
        private ticketService: TicketService,
        private stateService: StateService,
        private domainService: DomainService,
        private categoryService: CategoryService,
        private priorityService: PriorityService,
        private groupService: GroupService,
        private issuetypeService: IssuetypeService,
        private incidenttypeService: IncidenttypeService,
        private impactService: ImpactService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stk }) => {
            this.stk = stk;
            this.stk_start_date = this.stk.stk_start_date != null ? this.stk.stk_start_date.format(DATE_TIME_FORMAT) : null;
            this.requested_date = this.stk.requested_date != null ? this.stk.requested_date.format(DATE_TIME_FORMAT) : null;
            this.responded_time = this.stk.responded_time != null ? this.stk.responded_time.format(DATE_TIME_FORMAT) : null;
            this.modified_time = this.stk.modified_time != null ? this.stk.modified_time.format(DATE_TIME_FORMAT) : null;
            this.nttdata_start_date = this.stk.nttdata_start_date != null ? this.stk.nttdata_start_date.format(DATE_TIME_FORMAT) : null;
            this.rca_completed_date = this.stk.rca_completed_date != null ? this.stk.rca_completed_date.format(DATE_TIME_FORMAT) : null;
            this.solution_found_date = this.stk.solution_found_date != null ? this.stk.solution_found_date.format(DATE_TIME_FORMAT) : null;
            this.passed_back_date = this.stk.passed_back_date != null ? this.stk.passed_back_date.format(DATE_TIME_FORMAT) : null;
            this.re_assigned_date = this.stk.re_assigned_date != null ? this.stk.re_assigned_date.format(DATE_TIME_FORMAT) : null;
            this.passed_back_date1 = this.stk.passed_back_date1 != null ? this.stk.passed_back_date1.format(DATE_TIME_FORMAT) : null;
            this.re_assigned_date1 = this.stk.re_assigned_date1 != null ? this.stk.re_assigned_date1.format(DATE_TIME_FORMAT) : null;
            this.passed_back_date2 = this.stk.passed_back_date2 != null ? this.stk.passed_back_date2.format(DATE_TIME_FORMAT) : null;
            this.re_assigned_date2 = this.stk.re_assigned_date2 != null ? this.stk.re_assigned_date2.format(DATE_TIME_FORMAT) : null;
            this.passed_back_date3 = this.stk.passed_back_date3 != null ? this.stk.passed_back_date3.format(DATE_TIME_FORMAT) : null;
            this.re_assigned_date3 = this.stk.re_assigned_date3 != null ? this.stk.re_assigned_date3.format(DATE_TIME_FORMAT) : null;
            this.passed_back_date4 = this.stk.passed_back_date4 != null ? this.stk.passed_back_date4.format(DATE_TIME_FORMAT) : null;
            this.re_assigned_date4 = this.stk.re_assigned_date4 != null ? this.stk.re_assigned_date4.format(DATE_TIME_FORMAT) : null;
            this.passed_back_date5 = this.stk.passed_back_date5 != null ? this.stk.passed_back_date5.format(DATE_TIME_FORMAT) : null;
            this.re_assigned_date5 = this.stk.re_assigned_date5 != null ? this.stk.re_assigned_date5.format(DATE_TIME_FORMAT) : null;
            this.closed_date = this.stk.closed_date != null ? this.stk.closed_date.format(DATE_TIME_FORMAT) : null;
            this.ter_date = this.stk.ter_date != null ? this.stk.ter_date.format(DATE_TIME_FORMAT) : null;
            this.rrt_start_date = this.stk.rrt_start_date != null ? this.stk.rrt_start_date.format(DATE_TIME_FORMAT) : null;
            this.rrt_completion_date = this.stk.rrt_completion_date != null ? this.stk.rrt_completion_date.format(DATE_TIME_FORMAT) : null;
            this.live_date = this.stk.live_date != null ? this.stk.live_date.format(DATE_TIME_FORMAT) : null;
            this.finaldate_rca = this.stk.finaldate_rca != null ? this.stk.finaldate_rca.format(DATE_TIME_FORMAT) : null;
            this.finaldate_solution = this.stk.finaldate_solution != null ? this.stk.finaldate_solution.format(DATE_TIME_FORMAT) : null;
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
        this.impactService.query().subscribe(
            (res: HttpResponse<IImpact[]>) => {
                this.impacts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.stk.stk_start_date = this.stk_start_date != null ? moment(this.stk_start_date, DATE_TIME_FORMAT) : null;
        this.stk.requested_date = this.requested_date != null ? moment(this.requested_date, DATE_TIME_FORMAT) : null;
        this.stk.responded_time = this.responded_time != null ? moment(this.responded_time, DATE_TIME_FORMAT) : null;
        this.stk.modified_time = this.modified_time != null ? moment(this.modified_time, DATE_TIME_FORMAT) : null;
        this.stk.nttdata_start_date = this.nttdata_start_date != null ? moment(this.nttdata_start_date, DATE_TIME_FORMAT) : null;
        this.stk.rca_completed_date = this.rca_completed_date != null ? moment(this.rca_completed_date, DATE_TIME_FORMAT) : null;
        this.stk.solution_found_date = this.solution_found_date != null ? moment(this.solution_found_date, DATE_TIME_FORMAT) : null;
        this.stk.passed_back_date = this.passed_back_date != null ? moment(this.passed_back_date, DATE_TIME_FORMAT) : null;
        this.stk.re_assigned_date = this.re_assigned_date != null ? moment(this.re_assigned_date, DATE_TIME_FORMAT) : null;
        this.stk.passed_back_date1 = this.passed_back_date1 != null ? moment(this.passed_back_date1, DATE_TIME_FORMAT) : null;
        this.stk.re_assigned_date1 = this.re_assigned_date1 != null ? moment(this.re_assigned_date1, DATE_TIME_FORMAT) : null;
        this.stk.passed_back_date2 = this.passed_back_date2 != null ? moment(this.passed_back_date2, DATE_TIME_FORMAT) : null;
        this.stk.re_assigned_date2 = this.re_assigned_date2 != null ? moment(this.re_assigned_date2, DATE_TIME_FORMAT) : null;
        this.stk.passed_back_date3 = this.passed_back_date3 != null ? moment(this.passed_back_date3, DATE_TIME_FORMAT) : null;
        this.stk.re_assigned_date3 = this.re_assigned_date3 != null ? moment(this.re_assigned_date3, DATE_TIME_FORMAT) : null;
        this.stk.passed_back_date4 = this.passed_back_date4 != null ? moment(this.passed_back_date4, DATE_TIME_FORMAT) : null;
        this.stk.re_assigned_date4 = this.re_assigned_date4 != null ? moment(this.re_assigned_date4, DATE_TIME_FORMAT) : null;
        this.stk.passed_back_date5 = this.passed_back_date5 != null ? moment(this.passed_back_date5, DATE_TIME_FORMAT) : null;
        this.stk.re_assigned_date5 = this.re_assigned_date5 != null ? moment(this.re_assigned_date5, DATE_TIME_FORMAT) : null;
        this.stk.closed_date = this.closed_date != null ? moment(this.closed_date, DATE_TIME_FORMAT) : null;
        this.stk.ter_date = this.ter_date != null ? moment(this.ter_date, DATE_TIME_FORMAT) : null;
        this.stk.rrt_start_date = this.rrt_start_date != null ? moment(this.rrt_start_date, DATE_TIME_FORMAT) : null;
        this.stk.rrt_completion_date = this.rrt_completion_date != null ? moment(this.rrt_completion_date, DATE_TIME_FORMAT) : null;
        this.stk.live_date = this.live_date != null ? moment(this.live_date, DATE_TIME_FORMAT) : null;
        this.stk.finaldate_rca = this.finaldate_rca != null ? moment(this.finaldate_rca, DATE_TIME_FORMAT) : null;
        this.stk.finaldate_solution = this.finaldate_solution != null ? moment(this.finaldate_solution, DATE_TIME_FORMAT) : null;
        if (this.stk.id !== undefined) {
            this.subscribeToSaveResponse(this.stkService.update(this.stk));
        } else {
            this.subscribeToSaveResponse(this.stkService.create(this.stk));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStk>>) {
        result.subscribe((res: HttpResponse<IStk>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackImpactById(index: number, item: IImpact) {
        return item.id;
    }
}
