import { Moment } from 'moment';
import { IOptions } from 'app/shared/model//options.model';
import { IGroup } from 'app/shared/model//group.model';
import { ISeverity } from 'app/shared/model//severity.model';

export interface ITest_tracker {
    id?: number;
    elf_id?: string;
    defect_number?: string;
    defect_severity?: string;
    domain_name?: string;
    project_name?: string;
    test_phase?: string;
    detected_on_date?: Moment;
    first_fix_date?: Moment;
    last_fix_date?: Moment;
    fix_due_date?: Moment;
    status?: string;
    resolution?: string;
    closing_date?: Moment;
    priority?: string;
    problem_category?: string;
    comments?: string;
    modified_time?: Moment;
    user_id?: number;
    km1?: string;
    km1_breached?: boolean;
    km2?: string;
    km2_breached?: boolean;
    qm1?: string;
    qm1_breached?: boolean;
    qm2?: string;
    qm2_breached?: boolean;
    qm3?: string;
    qm3_breached?: boolean;
    assingned_in_qc?: IOptions;
    code_fix?: IOptions;
    concession_defect?: IOptions;
    group?: IGroup;
    severity?: ISeverity;
}

export class Test_tracker implements ITest_tracker {
    constructor(
        public id?: number,
        public elf_id?: string,
        public defect_number?: string,
        public defect_severity?: string,
        public domain_name?: string,
        public project_name?: string,
        public test_phase?: string,
        public detected_on_date?: Moment,
        public first_fix_date?: Moment,
        public last_fix_date?: Moment,
        public fix_due_date?: Moment,
        public status?: string,
        public resolution?: string,
        public closing_date?: Moment,
        public priority?: string,
        public problem_category?: string,
        public comments?: string,
        public modified_time?: Moment,
        public user_id?: number,
        public km1?: string,
        public km1_breached?: boolean,
        public km2?: string,
        public km2_breached?: boolean,
        public qm1?: string,
        public qm1_breached?: boolean,
        public qm2?: string,
        public qm2_breached?: boolean,
        public qm3?: string,
        public qm3_breached?: boolean,
        public assingned_in_qc?: IOptions,
        public code_fix?: IOptions,
        public concession_defect?: IOptions,
        public group?: IGroup,
        public severity?: ISeverity
    ) {
        this.km1_breached = this.km1_breached || false;
        this.km2_breached = this.km2_breached || false;
        this.qm1_breached = this.qm1_breached || false;
        this.qm2_breached = this.qm2_breached || false;
        this.qm3_breached = this.qm3_breached || false;
    }
}
