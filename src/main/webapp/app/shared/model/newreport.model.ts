export interface INewreport {
    id?: number;
    reportnameContentType?: string;
    reportname?: any;
}

export class Newreport implements INewreport {
    constructor(public id?: number, public reportnameContentType?: string, public reportname?: any) {}
}
