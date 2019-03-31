import { Moment } from 'moment';
import { IGroup } from 'app/shared/model//group.model';
import { IDevpriority } from 'app/shared/model//devpriority.model';
import { IDevservice } from 'app/shared/model//devservice.model';

export interface IDevelopment_tracker {
    id?: number;
    elf_id?: string;
    project_name?: string;
    request_date?: Moment;
    ack_date?: Moment;
    actual_effort_design_development?: number;
    actual_effort_development?: number;
    estimated_development_cost_iia?: string;
    cost_ready_date_actual?: Moment;
    delivery_to_test_planned?: Moment;
    delivery_to_test_actual?: Moment;
    test_completed?: string;
    defect_detected_s1?: number;
    defect_detected_s2?: number;
    defect_detected_s3?: number;
    defect_detected_s4?: number;
    defect_detected_s5?: number;
    delivery_to_production_planned?: Moment;
    delivery_to_production_actual?: Moment;
    implemented_successfully?: string;
    incident_p1?: number;
    incident_p2?: number;
    incident_p3?: number;
    incident_p4?: number;
    incident_p5?: number;
    incident_p6?: number;
    defect_date_raised?: Moment;
    critical_incident_s3_open?: number;
    hold_status?: string;
    comments?: string;
    modified_time?: Moment;
    sow_submission_date?: Moment;
    kpi1?: string;
    kpi1_breached?: boolean;
    kpi2?: string;
    kpi2_breached?: boolean;
    km1?: string;
    km1_breached?: boolean;
    qm1?: string;
    qm1_breached?: boolean;
    qm2?: string;
    qm2_breached?: boolean;
    finaldate?: Moment;
    group?: IGroup;
    priority?: IDevpriority;
    service_type?: IDevservice;
}

export class Development_tracker implements IDevelopment_tracker {
    constructor(
        public id?: number,
        public elf_id?: string,
        public project_name?: string,
        public request_date?: Moment,
        public ack_date?: Moment,
        public actual_effort_design_development?: number,
        public actual_effort_development?: number,
        public estimated_development_cost_iia?: string,
        public cost_ready_date_actual?: Moment,
        public delivery_to_test_planned?: Moment,
        public delivery_to_test_actual?: Moment,
        public test_completed?: string,
        public defect_detected_s1?: number,
        public defect_detected_s2?: number,
        public defect_detected_s3?: number,
        public defect_detected_s4?: number,
        public defect_detected_s5?: number,
        public delivery_to_production_planned?: Moment,
        public delivery_to_production_actual?: Moment,
        public implemented_successfully?: string,
        public incident_p1?: number,
        public incident_p2?: number,
        public incident_p3?: number,
        public incident_p4?: number,
        public incident_p5?: number,
        public incident_p6?: number,
        public defect_date_raised?: Moment,
        public critical_incident_s3_open?: number,
        public hold_status?: string,
        public comments?: string,
        public modified_time?: Moment,
        public sow_submission_date?: Moment,
        public kpi1?: string,
        public kpi1_breached?: boolean,
        public kpi2?: string,
        public kpi2_breached?: boolean,
        public km1?: string,
        public km1_breached?: boolean,
        public qm1?: string,
        public qm1_breached?: boolean,
        public qm2?: string,
        public qm2_breached?: boolean,
        public finaldate?: Moment,
        public group?: IGroup,
        public priority?: IDevpriority,
        public service_type?: IDevservice
    ) {
        this.kpi1_breached = this.kpi1_breached || false;
        this.kpi2_breached = this.kpi2_breached || false;
        this.km1_breached = this.km1_breached || false;
        this.qm1_breached = this.qm1_breached || false;
        this.qm2_breached = this.qm2_breached || false;
    }
}
