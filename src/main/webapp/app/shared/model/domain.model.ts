import { ITicket } from 'app/shared/model//ticket.model';

export interface IDomain {
    id?: number;
    domain_name?: string;
    ticket?: ITicket;
}

export class Domain implements IDomain {
    constructor(public id?: number, public domain_name?: string, public ticket?: ITicket) {}
}
