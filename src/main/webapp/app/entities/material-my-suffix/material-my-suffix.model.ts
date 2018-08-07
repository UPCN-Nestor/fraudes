import { BaseEntity } from './../../shared';

export class MaterialMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public descripcion?: string,
        public codigo?: string,
    ) {
    }
}
