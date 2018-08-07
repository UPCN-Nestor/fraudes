import { BaseEntity } from './../../shared';

export class Insumo implements BaseEntity {
    constructor(
        public id?: number,
        public cantidad?: number,
        public esEditable?: boolean,
        public trabajo?: BaseEntity,
        public material?: BaseEntity,
    ) {
        this.esEditable = false;
    }
}
