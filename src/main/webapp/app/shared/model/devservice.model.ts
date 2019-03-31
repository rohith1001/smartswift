export interface IDevservice {
    id?: number;
    service_type?: string;
}

export class Devservice implements IDevservice {
    constructor(public id?: number, public service_type?: string) {}
}
