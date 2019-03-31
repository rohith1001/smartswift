import { ITicket } from 'app/shared/model//ticket.model';

export interface IIncidenttype {
    id?: number;
    incident_name?: string;
    ticket?: ITicket;
}

export class Incidenttype implements IIncidenttype {
    constructor(public id?: number, public incident_name?: string, public ticket?: ITicket) {}
}
