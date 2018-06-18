import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { EstadoMySuffix } from './estado-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EstadoMySuffix>;

@Injectable()
export class EstadoMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/estados';

    constructor(private http: HttpClient) { }

    create(estado: EstadoMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(estado);
        return this.http.post<EstadoMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(estado: EstadoMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(estado);
        return this.http.put<EstadoMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EstadoMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EstadoMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<EstadoMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EstadoMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EstadoMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EstadoMySuffix[]>): HttpResponse<EstadoMySuffix[]> {
        const jsonResponse: EstadoMySuffix[] = res.body;
        const body: EstadoMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EstadoMySuffix.
     */
    private convertItemFromServer(estado: EstadoMySuffix): EstadoMySuffix {
        const copy: EstadoMySuffix = Object.assign({}, estado);
        return copy;
    }

    /**
     * Convert a EstadoMySuffix to a JSON which can be sent to the server.
     */
    private convert(estado: EstadoMySuffix): EstadoMySuffix {
        const copy: EstadoMySuffix = Object.assign({}, estado);
        return copy;
    }
}
