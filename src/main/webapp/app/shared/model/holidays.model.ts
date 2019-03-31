import { Moment } from 'moment';

export interface IHolidays {
    id?: number;
    holiday_date?: Moment;
}

export class Holidays implements IHolidays {
    constructor(public id?: number, public holiday_date?: Moment) {}
}
