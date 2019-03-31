import { ITicket } from 'app/shared/model//ticket.model';

export interface IState {
    id?: number;
    state_name?: string;
    ticket?: ITicket;
}

export class State implements IState {
    constructor(public id?: number, public state_name?: string, public ticket?: ITicket) {}
}
