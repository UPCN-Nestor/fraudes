import { BaseEntity } from './../../shared';

export class InspeccionMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public orden?: number,
        public fecha?: any,
        public observaciones?: string,
        public deshabitada?: boolean,
        public anomaliaMedidors?: BaseEntity[],
        public trabajos?: BaseEntity[],
        public inmueble?: BaseEntity,
        public etapa?: BaseEntity,
        public estado?: BaseEntity,
        public tipoInmueble?: BaseEntity,
    ) {
        this.deshabitada = false;
    }
}
