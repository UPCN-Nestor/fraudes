import { BaseEntity } from './../../shared';

export class EtapaMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public numero?: number,
        public descripcionCorta?: string,
        public descripcionLarga?: string,
        public inspeccions?: BaseEntity[],
    ) {
    }
}
