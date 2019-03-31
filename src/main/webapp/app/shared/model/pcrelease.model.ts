import { Moment } from 'moment';

export interface IPcrelease {
    id?: number;
    system?: string;
    release_date?: Moment;
    testedbyamdocs?: string;
    total_effort?: number;
}

export class Pcrelease implements IPcrelease {
    constructor(
        public id?: number,
        public system?: string,
        public release_date?: Moment,
        public testedbyamdocs?: string,
        public total_effort?: number
    ) {}
}
