export interface ISeverity {
    id?: number;
    severity_name?: string;
}

export class Severity implements ISeverity {
    constructor(public id?: number, public severity_name?: string) {}
}
