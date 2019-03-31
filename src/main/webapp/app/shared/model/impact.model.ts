export interface IImpact {
    id?: number;
    impact_name?: string;
}

export class Impact implements IImpact {
    constructor(public id?: number, public impact_name?: string) {}
}
