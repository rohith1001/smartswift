import { Moment } from 'moment';

export interface IPcincident {
    id?: number;
    reference?: string;
    title?: string;
    system?: string;
    date_recieved?: Moment;
    priority?: string;
    request_id?: string;
    release_date?: Moment;
    del_six_month?: string;
    user_id?: number;
}

export class Pcincident implements IPcincident {
    constructor(
        public id?: number,
        public reference?: string,
        public title?: string,
        public system?: string,
        public date_recieved?: Moment,
        public priority?: string,
        public request_id?: string,
        public release_date?: Moment,
        public del_six_month?: string,
        public user_id?: number
    ) {}
}
