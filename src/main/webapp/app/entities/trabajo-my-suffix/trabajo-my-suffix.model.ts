import { BaseEntity } from './../../shared';

export class TrabajoMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public descripcion?: string,
        public costo?: number,
        public usaMedidor?: boolean,
        public usaCable?: boolean,
        public costoTrif?: number,
        public inspeccions?: BaseEntity[],
    ) {
        this.usaMedidor = false;
        this.usaCable = false;
    }
}
