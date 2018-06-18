import { BaseEntity } from './../../shared';

export class InmuebleMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public direccion?: string,
        public inspeccions?: BaseEntity[],
    ) {
    }
}
