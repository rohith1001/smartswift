import { Moment } from 'moment';

export interface IReports {
    id?: number;
    reportContentType?: string;
    report?: any;
    generated_on?: Moment;
}

export class Reports implements IReports {
    constructor(public id?: number, public reportContentType?: string, public report?: any, public generated_on?: Moment) {}
}
