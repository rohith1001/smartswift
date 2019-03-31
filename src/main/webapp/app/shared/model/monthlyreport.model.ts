import { Moment } from 'moment';

export interface IMonthlyreport {
    id?: number;
    from_date?: Moment;
    to_date?: Moment;
}

export class Monthlyreport implements IMonthlyreport {
    constructor(public id?: number, public from_date?: Moment, public to_date?: Moment) {}
}
