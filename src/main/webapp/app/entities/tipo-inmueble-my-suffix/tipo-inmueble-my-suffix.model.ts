import { BaseEntity } from './../../shared';

export class TipoInmuebleMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public descripcion?: string,
        public inspeccions?: BaseEntity[],
    ) {
    }
}
