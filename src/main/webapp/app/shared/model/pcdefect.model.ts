import { Moment } from 'moment';

export interface IPcdefect {
    id?: number;
    defect_id?: number;
    description?: string;
    system_impacted?: string;
    date_raised?: Moment;
    severity?: string;
    date_closed?: Moment;
    request_id?: string;
    release_date?: Moment;
    comments?: string;
    root_cause?: string;
    user_id?: number;
    testedbyamdocs?: string;
}

export class Pcdefect implements IPcdefect {
    constructor(
        public id?: number,
        public defect_id?: number,
        public description?: string,
        public system_impacted?: string,
        public date_raised?: Moment,
        public severity?: string,
        public date_closed?: Moment,
        public request_id?: string,
        public release_date?: Moment,
        public comments?: string,
        public root_cause?: string,
        public user_id?: number,
        public testedbyamdocs?: string
    ) {}
}
