import { Moment } from 'moment';
import { IGroup } from 'app/shared/model//group.model';

export interface IHld {
    id?: number;
    elf_id?: string;
    project_name?: string;
    request_date?: Moment;
    actual_acknowledgement_date?: Moment;
    delivery_date_planned?: Moment;
    delivery_date_actual?: Moment;
    agreed_date?: Moment;
    hold_flag?: string;
    hold_date?: Moment;
    hold_days?: number;
    modified_time?: Moment;
    comments?: string;
    wif_submission_date?: Moment;
    kpi1?: string;
    kpi1_breached?: boolean;
    qm1?: string;
    qm1_breached?: boolean;
    user_id?: number;
    qm2?: string;
    qm2_breached?: boolean;
    finaldate?: Moment;
    requestor?: string;
    group?: IGroup;
}

export class Hld implements IHld {
    constructor(
        public id?: number,
        public elf_id?: string,
        public project_name?: string,
        public request_date?: Moment,
        public actual_acknowledgement_date?: Moment,
        public delivery_date_planned?: Moment,
        public delivery_date_actual?: Moment,
        public agreed_date?: Moment,
        public hold_flag?: string,
        public hold_date?: Moment,
        public hold_days?: number,
        public modified_time?: Moment,
        public comments?: string,
        public wif_submission_date?: Moment,
        public kpi1?: string,
        public kpi1_breached?: boolean,
        public qm1?: string,
        public qm1_breached?: boolean,
        public user_id?: number,
        public qm2?: string,
        public qm2_breached?: boolean,
        public finaldate?: Moment,
        public requestor?: string,
        public group?: IGroup
    ) {
        this.kpi1_breached = this.kpi1_breached || false;
        this.qm1_breached = this.qm1_breached || false;
        this.qm2_breached = this.qm2_breached || false;
    }
}
