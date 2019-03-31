export interface IOptions {
    id?: number;
    option_type?: string;
}

export class Options implements IOptions {
    constructor(public id?: number, public option_type?: string) {}
}
