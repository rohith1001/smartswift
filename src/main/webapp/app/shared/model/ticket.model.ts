export interface ITicket {
    id?: number;
    ticket_type?: string;
}

export class Ticket implements ITicket {
    constructor(public id?: number, public ticket_type?: string) {}
}
