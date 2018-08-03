import { BaseEntity } from './../../shared';

export class Insumo implements BaseEntity {
    constructor(
        public id?: number,
        public cantidad?: number,
        public trabajo?: BaseEntity,
        public material?: BaseEntity,
    ) {
    }
}
