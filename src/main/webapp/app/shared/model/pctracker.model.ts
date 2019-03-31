import { Moment } from 'moment';
import { IConfigtype } from 'app/shared/model//configtype.model';
import { IElf_status } from 'app/shared/model//elf-status.model';

export interface IPctracker {
    id?: number;
    elf_id?: string;
    title?: string;
    system?: string;
    date_received?: Moment;
    iia_deliver_date_planned?: Moment;
    iia_deliver_date_actual?: Moment;
    dcd_deliver_date_planned?: Moment;
    dcd_deliver_date_actual?: Moment;
    wr_acknowledge_date_planned?: Moment;
    wr_acknowledge_date_actual?: Moment;
    wr_costready_date_planned?: Moment;
    wr_costready_date_actual?: Moment;
    hlcd_delivery_date_planned?: Moment;
    hlcd_delivery_date_actual?: Moment;
    test_ready_date_planned?: Moment;
    test_ready_date_actual?: Moment;
    launch_date_planned?: Moment;
    launch_date_actual?: Moment;
    delivery_date_planned?: Moment;
    delivery_date_actual?: Moment;
    comments?: string;
    modified_time?: Moment;
    user_id?: number;
    major?: number;
    minor?: number;
    cosmetic?: number;
    kpi1?: string;
    kpi1_breached?: boolean;
    kpi2?: string;
    kpi2_breached?: boolean;
    kpi3?: string;
    kpi3_breached?: boolean;
    km1?: string;
    km1_breached?: boolean;
    km2?: string;
    km2_breached?: boolean;
    km3?: string;
    km3_breached?: boolean;
    km4?: string;
    km4_breached?: boolean;
    qm1?: string;
    qm1_breached?: boolean;
    qm2?: string;
    qm2_breached?: boolean;
    configtype?: IConfigtype;
    elf_status?: IElf_status;
}

export class Pctracker implements IPctracker {
    constructor(
        public id?: number,
        public elf_id?: string,
        public title?: string,
        public system?: string,
        public date_received?: Moment,
        public iia_deliver_date_planned?: Moment,
        public iia_deliver_date_actual?: Moment,
        public dcd_deliver_date_planned?: Moment,
        public dcd_deliver_date_actual?: Moment,
        public wr_acknowledge_date_planned?: Moment,
        public wr_acknowledge_date_actual?: Moment,
        public wr_costready_date_planned?: Moment,
        public wr_costready_date_actual?: Moment,
        public hlcd_delivery_date_planned?: Moment,
        public hlcd_delivery_date_actual?: Moment,
        public test_ready_date_planned?: Moment,
        public test_ready_date_actual?: Moment,
        public launch_date_planned?: Moment,
        public launch_date_actual?: Moment,
        public delivery_date_planned?: Moment,
        public delivery_date_actual?: Moment,
        public comments?: string,
        public modified_time?: Moment,
        public user_id?: number,
        public major?: number,
        public minor?: number,
        public cosmetic?: number,
        public kpi1?: string,
        public kpi1_breached?: boolean,
        public kpi2?: string,
        public kpi2_breached?: boolean,
        public kpi3?: string,
        public kpi3_breached?: boolean,
        public km1?: string,
        public km1_breached?: boolean,
        public km2?: string,
        public km2_breached?: boolean,
        public km3?: string,
        public km3_breached?: boolean,
        public km4?: string,
        public km4_breached?: boolean,
        public qm1?: string,
        public qm1_breached?: boolean,
        public qm2?: string,
        public qm2_breached?: boolean,
        public configtype?: IConfigtype,
        public elf_status?: IElf_status
    ) {
        this.kpi1_breached = this.kpi1_breached || false;
        this.kpi2_breached = this.kpi2_breached || false;
        this.kpi3_breached = this.kpi3_breached || false;
        this.km1_breached = this.km1_breached || false;
        this.km2_breached = this.km2_breached || false;
        this.km3_breached = this.km3_breached || false;
        this.km4_breached = this.km4_breached || false;
        this.qm1_breached = this.qm1_breached || false;
        this.qm2_breached = this.qm2_breached || false;
    }
}
