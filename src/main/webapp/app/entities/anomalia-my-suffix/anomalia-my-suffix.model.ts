import { BaseEntity } from './../../shared';

export class AnomaliaMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public descripcion?: string,
        public inspeccions?: BaseEntity[],
    ) {
    }
}
