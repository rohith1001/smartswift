import { Moment } from 'moment';

export interface IL3_query {
    id?: number;
    stk_number?: string;
    stk_description?: string;
    stk_start_date?: Moment;
    requested_date?: Moment;
}

export class L3_query implements IL3_query {
    constructor(
        public id?: number,
        public stk_number?: string,
        public stk_description?: string,
        public stk_start_date?: Moment,
        public requested_date?: Moment
    ) {}
}
