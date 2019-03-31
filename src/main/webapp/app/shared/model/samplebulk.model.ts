export interface ISamplebulk {
    id?: number;
    filenameContentType?: string;
    filename?: any;
}

export class Samplebulk implements ISamplebulk {
    constructor(public id?: number, public filenameContentType?: string, public filename?: any) {}
}
