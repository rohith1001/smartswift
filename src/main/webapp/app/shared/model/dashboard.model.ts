export interface IDashboard {
    id?: number;
    reference_number?: string;
    sla_description?: string;
    expected?: string;
    minimum?: string;
    sla_complaince?: string;
}

export class Dashboard implements IDashboard {
    constructor(
        public id?: number,
        public reference_number?: string,
        public sla_description?: string,
        public expected?: string,
        public minimum?: string,
        public sla_complaince?: string
    ) {}
}
