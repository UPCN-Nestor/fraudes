import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { createRequestOption } from '../../shared';


@Injectable()
export class GraficosService {

    private resourceUrl =  SERVER_API_URL + 'api/' ;

    constructor(private http: HttpClient) { }

    byTipoTrabajo(etapa_id:number, req?: any): Observable<HttpResponse<any>> {
        const options = createRequestOption(req);
        return this.http.get<any>(this.resourceUrl + 'inspeccions/bytipotrabajo/' + etapa_id, { params: options, observe: 'response' });
    }

    byAnomalia(etapa_id:number, req?: any): Observable<HttpResponse<any>> {
        const options = createRequestOption(req);
        return this.http.get<any>(this.resourceUrl + 'inspeccions/byanomalia/' + etapa_id, { params: options, observe: 'response' });
    }

    byFecha(etapa_id:number, req?: any): Observable<HttpResponse<any>> {
        const options = createRequestOption(req);
        return this.http.get<any>(this.resourceUrl + 'inspeccions/byfecha/' + etapa_id, { params: options, observe: 'response' });
    }
}
