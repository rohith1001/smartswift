import { Moment } from 'moment';
import { IGroup } from 'app/shared/model//group.model';
import { IResolved } from 'app/shared/model//resolved.model';

export interface IIia {
    id?: number;
    elf_id?: string;
    project_name?: string;
    request_date?: Moment;
    actual_acknowledgement_date?: Moment;
    delivery_date_planned?: Moment;
    delivery_date_actual?: Moment;
    agreed_date?: Moment;
    iia_resubmitted_date?: Moment;
    hold_flag?: string;
    hold_date?: Moment;
    hold_days?: number;
    modified_time?: Moment;
    requestor?: string;
    comments?: string;
    kpi1?: string;
    kpi1_breached?: boolean;
    qm1?: string;
    qm1_breached?: boolean;
    qm2?: string;
    qm2_breached?: boolean;
    finaldate?: Moment;
    group?: IGroup;
    resolved?: IResolved;
}

export class Iia implements IIia {
    constructor(
        public id?: number,
        public elf_id?: string,
        public project_name?: string,
        public request_date?: Moment,
        public actual_acknowledgement_date?: Moment,
        public delivery_date_planned?: Moment,
        public delivery_date_actual?: Moment,
        public agreed_date?: Moment,
        public iia_resubmitted_date?: Moment,
        public hold_flag?: string,
        public hold_date?: Moment,
        public hold_days?: number,
        public modified_time?: Moment,
        public requestor?: string,
        public comments?: string,
        public kpi1?: string,
        public kpi1_breached?: boolean,
        public qm1?: string,
        public qm1_breached?: boolean,
        public qm2?: string,
        public qm2_breached?: boolean,
        public finaldate?: Moment,
        public group?: IGroup,
        public resolved?: IResolved
    ) {
        this.kpi1_breached = this.kpi1_breached || false;
        this.qm1_breached = this.qm1_breached || false;
        this.qm2_breached = this.qm2_breached || false;
    }
}
