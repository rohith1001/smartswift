export interface IResolved {
    id?: number;
    state?: string;
}

export class Resolved implements IResolved {
    constructor(public id?: number, public state?: string) {}
}
