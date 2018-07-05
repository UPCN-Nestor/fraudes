import { BaseEntity } from './../../shared';

export class InmuebleMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public calle?: string,
        public altura?: string,
        public piso?: string,
        public depto?: string,
        public anexo?: string,
        public id_glm?: number,
        public inspeccions?: BaseEntity[],
    ) {
    }
}
