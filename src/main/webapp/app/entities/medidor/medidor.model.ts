import { BaseEntity } from './../../shared';

export class Medidor implements BaseEntity {
    constructor(
        public id?: number,
        public numero?: string,
        public inspeccion?: BaseEntity,
    ) {
    }
}
