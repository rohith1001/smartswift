export interface IBulkincident {
    id?: number;
    filenameContentType?: string;
    filename?: any;
}

export class Bulkincident implements IBulkincident {
    constructor(public id?: number, public filenameContentType?: string, public filename?: any) {}
}
