export interface IBulkrelease {
    id?: number;
    filenameContentType?: string;
    filename?: any;
}

export class Bulkrelease implements IBulkrelease {
    constructor(public id?: number, public filenameContentType?: string, public filename?: any) {}
}
