export interface IElf_status {
    id?: number;
    status?: string;
}

export class Elf_status implements IElf_status {
    constructor(public id?: number, public status?: string) {}
}
