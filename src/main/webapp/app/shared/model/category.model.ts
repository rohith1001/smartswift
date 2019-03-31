import { ITicket } from 'app/shared/model//ticket.model';

export interface ICategory {
    id?: number;
    category_name?: string;
    ticket?: ITicket;
}

export class Category implements ICategory {
    constructor(public id?: number, public category_name?: string, public ticket?: ITicket) {}
}
