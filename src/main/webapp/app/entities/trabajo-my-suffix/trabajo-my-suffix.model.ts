import { BaseEntity } from './../../shared';

export class TrabajoMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public descripcion?: string,
        public costo?: number,
        public usaMedidor?: boolean,
        public inspeccions?: BaseEntity[],
    ) {
        this.usaMedidor = false;
    }
}
