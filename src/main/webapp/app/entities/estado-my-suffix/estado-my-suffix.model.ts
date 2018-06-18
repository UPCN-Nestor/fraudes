import { BaseEntity } from './../../shared';

export class EstadoMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public descripcion?: string,
        public inspeccions?: BaseEntity[],
    ) {
    }
}
