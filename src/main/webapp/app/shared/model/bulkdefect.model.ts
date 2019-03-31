export interface IBulkdefect {
    id?: number;
    filenameContentType?: string;
    filename?: any;
}

export class Bulkdefect implements IBulkdefect {
    constructor(public id?: number, public filenameContentType?: string, public filename?: any) {}
}
