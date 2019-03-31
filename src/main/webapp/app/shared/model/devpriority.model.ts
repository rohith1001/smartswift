export interface IDevpriority {
    id?: number;
    priorty_name?: string;
}

export class Devpriority implements IDevpriority {
    constructor(public id?: number, public priorty_name?: string) {}
}
