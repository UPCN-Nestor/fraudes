import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TipoInmuebleMySuffix } from './tipo-inmueble-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TipoInmuebleMySuffix>;

@Injectable()
export class TipoInmuebleMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/tipo-inmuebles';

    constructor(private http: HttpClient) { }

    create(tipoInmueble: TipoInmuebleMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(tipoInmueble);
        return this.http.post<TipoInmuebleMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(tipoInmueble: TipoInmuebleMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(tipoInmueble);
        return this.http.put<TipoInmuebleMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TipoInmuebleMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TipoInmuebleMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<TipoInmuebleMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TipoInmuebleMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TipoInmuebleMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TipoInmuebleMySuffix[]>): HttpResponse<TipoInmuebleMySuffix[]> {
        const jsonResponse: TipoInmuebleMySuffix[] = res.body;
        const body: TipoInmuebleMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TipoInmuebleMySuffix.
     */
    private convertItemFromServer(tipoInmueble: TipoInmuebleMySuffix): TipoInmuebleMySuffix {
        const copy: TipoInmuebleMySuffix = Object.assign({}, tipoInmueble);
        return copy;
    }

    /**
     * Convert a TipoInmuebleMySuffix to a JSON which can be sent to the server.
     */
    private convert(tipoInmueble: TipoInmuebleMySuffix): TipoInmuebleMySuffix {
        const copy: TipoInmuebleMySuffix = Object.assign({}, tipoInmueble);
        return copy;
    }
}
