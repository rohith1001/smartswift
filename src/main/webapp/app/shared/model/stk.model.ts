import { Moment } from 'moment';
import { ITicket } from 'app/shared/model//ticket.model';
import { IState } from 'app/shared/model//state.model';
import { IDomain } from 'app/shared/model//domain.model';
import { ICategory } from 'app/shared/model//category.model';
import { IPriority } from 'app/shared/model//priority.model';
import { IGroup } from 'app/shared/model//group.model';
import { IIssuetype } from 'app/shared/model//issuetype.model';
import { IIncidenttype } from 'app/shared/model//incidenttype.model';
import { IImpact } from 'app/shared/model//impact.model';

export interface IStk {
    id?: number;
    stk_number?: string;
    stk_description?: string;
    stk_comment?: string;
    root_cause_description?: string;
    rca_fix_success?: string;
    stk_start_date?: Moment;
    requested_date?: Moment;
    responded_time?: Moment;
    modified_time?: Moment;
    nttdata_start_date?: Moment;
    rca_completed_date?: Moment;
    solution_found_date?: Moment;
    passed_back_date?: Moment;
    re_assigned_date?: Moment;
    passed_back_date1?: Moment;
    re_assigned_date1?: Moment;
    passed_back_date2?: Moment;
    re_assigned_date2?: Moment;
    passed_back_date3?: Moment;
    re_assigned_date3?: Moment;
    passed_back_date4?: Moment;
    re_assigned_date4?: Moment;
    passed_back_date5?: Moment;
    re_assigned_date5?: Moment;
    closed_date?: Moment;
    ter_date?: Moment;
    rrt_start_date?: Moment;
    rrt_completion_date?: Moment;
    live_date?: Moment;
    km1?: string;
    km1_breached?: boolean;
    km2?: string;
    km2_breached?: boolean;
    km3?: string;
    km3_breached?: boolean;
    qm1?: string;
    qm1_breached?: boolean;
    qm2?: string;
    qm2_breached?: boolean;
    qm3?: string;
    qm3_breached?: boolean;
    qm4?: string;
    qm4_breached?: boolean;
    rca_completed?: boolean;
    solution_found?: boolean;
    finaldate_rca?: Moment;
    finaldate_solution?: Moment;
    ticket?: ITicket;
    state?: IState;
    domain?: IDomain;
    category?: ICategory;
    priority?: IPriority;
    group?: IGroup;
    issuetype?: IIssuetype;
    incidenttype?: IIncidenttype;
    impact?: IImpact;
}

export class Stk implements IStk {
    constructor(
        public id?: number,
        public stk_number?: string,
        public stk_description?: string,
        public stk_comment?: string,
        public root_cause_description?: string,
        public rca_fix_success?: string,
        public stk_start_date?: Moment,
        public requested_date?: Moment,
        public responded_time?: Moment,
        public modified_time?: Moment,
        public nttdata_start_date?: Moment,
        public rca_completed_date?: Moment,
        public solution_found_date?: Moment,
        public passed_back_date?: Moment,
        public re_assigned_date?: Moment,
        public passed_back_date1?: Moment,
        public re_assigned_date1?: Moment,
        public passed_back_date2?: Moment,
        public re_assigned_date2?: Moment,
        public passed_back_date3?: Moment,
        public re_assigned_date3?: Moment,
        public passed_back_date4?: Moment,
        public re_assigned_date4?: Moment,
        public passed_back_date5?: Moment,
        public re_assigned_date5?: Moment,
        public closed_date?: Moment,
        public ter_date?: Moment,
        public rrt_start_date?: Moment,
        public rrt_completion_date?: Moment,
        public live_date?: Moment,
        public km1?: string,
        public km1_breached?: boolean,
        public km2?: string,
        public km2_breached?: boolean,
        public km3?: string,
        public km3_breached?: boolean,
        public qm1?: string,
        public qm1_breached?: boolean,
        public qm2?: string,
        public qm2_breached?: boolean,
        public qm3?: string,
        public qm3_breached?: boolean,
        public qm4?: string,
        public qm4_breached?: boolean,
        public rca_completed?: boolean,
        public solution_found?: boolean,
        public finaldate_rca?: Moment,
        public finaldate_solution?: Moment,
        public ticket?: ITicket,
        public state?: IState,
        public domain?: IDomain,
        public category?: ICategory,
        public priority?: IPriority,
        public group?: IGroup,
        public issuetype?: IIssuetype,
        public incidenttype?: IIncidenttype,
        public impact?: IImpact
    ) {
        this.km1_breached = this.km1_breached || false;
        this.km2_breached = this.km2_breached || false;
        this.km3_breached = this.km3_breached || false;
        this.qm1_breached = this.qm1_breached || false;
        this.qm2_breached = this.qm2_breached || false;
        this.qm3_breached = this.qm3_breached || false;
        this.qm4_breached = this.qm4_breached || false;
        this.rca_completed = this.rca_completed || false;
        this.solution_found = this.solution_found || false;
    }
}
