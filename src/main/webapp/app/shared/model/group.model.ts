import { IDomain } from 'app/shared/model//domain.model';
import { ITicket } from 'app/shared/model//ticket.model';

export interface IGroup {
    id?: number;
    group_name?: string;
    domain?: IDomain;
    ticket?: ITicket;
}

export class Group implements IGroup {
    constructor(public id?: number, public group_name?: string, public domain?: IDomain, public ticket?: ITicket) {}
}
