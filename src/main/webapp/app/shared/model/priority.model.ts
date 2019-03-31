import { Moment } from 'moment';
import { ITicket } from 'app/shared/model//ticket.model';

export interface IPriority {
    id?: number;
    priority_name?: string;
    update_frequency?: string;
    ack_time?: Moment;
    restore_time?: Moment;
    operation_hours?: string;
    operational_days?: number;
    ticket?: ITicket;
}

export class Priority implements IPriority {
    constructor(
        public id?: number,
        public priority_name?: string,
        public update_frequency?: string,
        public ack_time?: Moment,
        public restore_time?: Moment,
        public operation_hours?: string,
        public operational_days?: number,
        public ticket?: ITicket
    ) {}
}
