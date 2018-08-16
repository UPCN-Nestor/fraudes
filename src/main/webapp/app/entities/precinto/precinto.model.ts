import { BaseEntity } from './../../shared';

export class Precinto implements BaseEntity {
    constructor(
        public id?: number,
        public numero?: string,
        public color?: string,
        public inspeccion?: BaseEntity,
        public inspeccionBornera?: BaseEntity,
    ) {
    }
}
