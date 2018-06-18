import { BaseEntity } from './../../shared';

export class TrabajoMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public descripcion?: string,
        public costo?: number,
        public materials?: BaseEntity[],
        public inspeccions?: BaseEntity[],
    ) {
    }
}
