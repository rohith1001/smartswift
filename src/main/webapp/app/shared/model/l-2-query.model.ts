import { Moment } from 'moment';
import { ITicket } from 'app/shared/model//ticket.model';
import { IState } from 'app/shared/model//state.model';
import { IDomain } from 'app/shared/model//domain.model';
import { ICategory } from 'app/shared/model//category.model';
import { IImpact } from 'app/shared/model//impact.model';
import { IPriority } from 'app/shared/model//priority.model';
import { IGroup } from 'app/shared/model//group.model';
import { IIssuetype } from 'app/shared/model//issuetype.model';
import { IIncidenttype } from 'app/shared/model//incidenttype.model';

export interface IL2query {
    id?: number;
    stk_number?: string;
    stk_description?: string;
    stk_start_date?: Moment;
    requested_date?: Moment;
    responded_date?: Moment;
    stk_comment?: string;
    modified_time?: Moment;
    rca_completed?: number;
    rca_completed_date?: Moment;
    nttdata_start_date?: Moment;
    solution_found?: number;
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
    root_cause_description?: string;
    ter_date?: Moment;
    rrt_start_date?: Moment;
    rrt_completion_date?: Moment;
    live_date?: Moment;
    rca_fix_success?: number;
    ops_sla_breached?: boolean;
    ticket?: ITicket;
    state?: IState;
    domain?: IDomain;
    category?: ICategory;
    impact?: IImpact;
    priority?: IPriority;
    group?: IGroup;
    issuetype?: IIssuetype;
    incidenttype?: IIncidenttype;
}

export class L2query implements IL2query {
    constructor(
        public id?: number,
        public stk_number?: string,
        public stk_description?: string,
        public stk_start_date?: Moment,
        public requested_date?: Moment,
        public responded_date?: Moment,
        public stk_comment?: string,
        public modified_time?: Moment,
        public rca_completed?: number,
        public rca_completed_date?: Moment,
        public nttdata_start_date?: Moment,
        public solution_found?: number,
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
        public root_cause_description?: string,
        public ter_date?: Moment,
        public rrt_start_date?: Moment,
        public rrt_completion_date?: Moment,
        public live_date?: Moment,
        public rca_fix_success?: number,
        public ops_sla_breached?: boolean,
        public ticket?: ITicket,
        public state?: IState,
        public domain?: IDomain,
        public category?: ICategory,
        public impact?: IImpact,
        public priority?: IPriority,
        public group?: IGroup,
        public issuetype?: IIssuetype,
        public incidenttype?: IIncidenttype
    ) {
        this.ops_sla_breached = this.ops_sla_breached || false;
    }
}
