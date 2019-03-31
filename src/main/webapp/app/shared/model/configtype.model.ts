export interface IConfigtype {
    id?: number;
    type?: string;
}

export class Configtype implements IConfigtype {
    constructor(public id?: number, public type?: string) {}
}
