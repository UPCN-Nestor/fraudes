import { BaseEntity } from './../../shared';

export class InspeccionMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public orden?: number,
        public observaciones?: string,
        public deshabitada?: boolean,
        public usuario?: string,
        public fechahora?: any,
        public medidorInstalado?: string,
        public ultimaLectura?: number,
        public medidorRetirado?: string,
        public socio?: number,
        public suministro?: number,
        public nombre?: string,
        public tarifa?: string,
        public mtsCable?: number,
        public lecturaNuevo?: number,
        public fotoContentType?: string,
        public foto?: any,
        public estadoGLM?: string,
        public lecturaActual?: number,
        public fechaToma?: any,
        public medidorNuevoLibre?: string,
        public anomaliaMedidors?: BaseEntity[],
        public trabajos?: BaseEntity[],
        public inmueble?: BaseEntity,
        public etapa?: BaseEntity,
        public estado?: BaseEntity,
        public tipoInmueble?: BaseEntity,
        public medidorNuevo?: BaseEntity,
    ) {
        this.deshabitada = false;
    }
}
