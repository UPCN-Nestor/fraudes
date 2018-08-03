import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Insumo } from './insumo.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Insumo>;

@Injectable()
export class InsumoService {

    private resourceUrl =  SERVER_API_URL + 'api/insumos';

    constructor(private http: HttpClient) { }

    create(insumo: Insumo): Observable<EntityResponseType> {
        const copy = this.convert(insumo);
        return this.http.post<Insumo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(insumo: Insumo): Observable<EntityResponseType> {
        const copy = this.convert(insumo);
        return this.http.put<Insumo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Insumo>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Insumo[]>> {
        const options = createRequestOption(req);
        return this.http.get<Insumo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Insumo[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Insumo = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Insumo[]>): HttpResponse<Insumo[]> {
        const jsonResponse: Insumo[] = res.body;
        const body: Insumo[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Insumo.
     */
    private convertItemFromServer(insumo: Insumo): Insumo {
        const copy: Insumo = Object.assign({}, insumo);
        return copy;
    }

    /**
     * Convert a Insumo to a JSON which can be sent to the server.
     */
    private convert(insumo: Insumo): Insumo {
        const copy: Insumo = Object.assign({}, insumo);
        return copy;
    }
}
