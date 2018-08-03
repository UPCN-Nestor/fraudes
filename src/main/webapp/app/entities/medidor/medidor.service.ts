import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Medidor } from './medidor.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Medidor>;

@Injectable()
export class MedidorService {

    private resourceUrl =  SERVER_API_URL + 'api/medidors';

    constructor(private http: HttpClient) { }

    create(medidor: Medidor): Observable<EntityResponseType> {
        const copy = this.convert(medidor);
        return this.http.post<Medidor>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(medidor: Medidor): Observable<EntityResponseType> {
        const copy = this.convert(medidor);
        return this.http.put<Medidor>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Medidor>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Medidor[]>> {
        const options = createRequestOption(req);
        return this.http.get<Medidor[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Medidor[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Medidor = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Medidor[]>): HttpResponse<Medidor[]> {
        const jsonResponse: Medidor[] = res.body;
        const body: Medidor[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Medidor.
     */
    private convertItemFromServer(medidor: Medidor): Medidor {
        const copy: Medidor = Object.assign({}, medidor);
        return copy;
    }

    /**
     * Convert a Medidor to a JSON which can be sent to the server.
     */
    private convert(medidor: Medidor): Medidor {
        const copy: Medidor = Object.assign({}, medidor);
        return copy;
    }
}
