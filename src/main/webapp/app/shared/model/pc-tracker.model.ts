import { Moment } from 'moment';
import { IElf_status } from 'app/shared/model//elf-status.model';
import { IConfigtype } from 'app/shared/model//configtype.model';

export interface IPc_tracker {
    id?: number;
    elf_id?: string;
    title?: string;
    system?: string;
    date_received?: Moment;
    iia_delivery_date_planned?: Moment;
    iia_delivery_date_actual?: Moment;
    dcd_delivery_date_planned?: Moment;
    dcd_delivery_date_actual?: Moment;
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
    major?: number;
    minor?: number;
    cosmetic?: number;
    elf_status?: IElf_status;
    configtype?: IConfigtype;
}

export class Pc_tracker implements IPc_tracker {
    constructor(
        public id?: number,
        public elf_id?: string,
        public title?: string,
        public system?: string,
        public date_received?: Moment,
        public iia_delivery_date_planned?: Moment,
        public iia_delivery_date_actual?: Moment,
        public dcd_delivery_date_planned?: Moment,
        public dcd_delivery_date_actual?: Moment,
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
        public major?: number,
        public minor?: number,
        public cosmetic?: number,
        public elf_status?: IElf_status,
        public configtype?: IConfigtype
    ) {}
}
