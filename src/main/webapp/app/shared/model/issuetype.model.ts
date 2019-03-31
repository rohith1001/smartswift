import { ITicket } from 'app/shared/model//ticket.model';

export interface IIssuetype {
    id?: number;
    issue_name?: string;
    ticket?: ITicket;
}

export class Issuetype implements IIssuetype {
    constructor(public id?: number, public issue_name?: string, public ticket?: ITicket) {}
}
