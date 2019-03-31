export interface IConfigslas {
    id?: number;
    qm1?: string;
    qm1_breached?: boolean;
    qm2?: string;
    qm2_breached?: boolean;
}

export class Configslas implements IConfigslas {
    constructor(
        public id?: number,
        public qm1?: string,
        public qm1_breached?: boolean,
        public qm2?: string,
        public qm2_breached?: boolean
    ) {
        this.qm1_breached = this.qm1_breached || false;
        this.qm2_breached = this.qm2_breached || false;
    }
}
