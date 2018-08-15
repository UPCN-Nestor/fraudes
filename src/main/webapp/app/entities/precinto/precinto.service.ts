import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Precinto } from './precinto.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Precinto>;

@Injectable()
export class PrecintoService {

    private resourceUrl =  SERVER_API_URL + 'api/precintos';

    constructor(private http: HttpClient) { }

    create(precinto: Precinto): Observable<EntityResponseType> {
        const copy = this.convert(precinto);
        return this.http.post<Precinto>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(precinto: Precinto): Observable<EntityResponseType> {
        const copy = this.convert(precinto);
        return this.http.put<Precinto>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Precinto>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Precinto[]>> {
        const options = createRequestOption(req);
        return this.http.get<Precinto[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Precinto[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Precinto = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Precinto[]>): HttpResponse<Precinto[]> {
        const jsonResponse: Precinto[] = res.body;
        const body: Precinto[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Precinto.
     */
    private convertItemFromServer(precinto: Precinto): Precinto {
        const copy: Precinto = Object.assign({}, precinto);
        return copy;
    }

    /**
     * Convert a Precinto to a JSON which can be sent to the server.
     */
    private convert(precinto: Precinto): Precinto {
        const copy: Precinto = Object.assign({}, precinto);
        return copy;
    }
}
