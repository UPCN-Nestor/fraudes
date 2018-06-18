import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { EtapaMySuffix } from './etapa-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EtapaMySuffix>;

@Injectable()
export class EtapaMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/etapas';

    constructor(private http: HttpClient) { }

    create(etapa: EtapaMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(etapa);
        return this.http.post<EtapaMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(etapa: EtapaMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(etapa);
        return this.http.put<EtapaMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EtapaMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EtapaMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<EtapaMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EtapaMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EtapaMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EtapaMySuffix[]>): HttpResponse<EtapaMySuffix[]> {
        const jsonResponse: EtapaMySuffix[] = res.body;
        const body: EtapaMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EtapaMySuffix.
     */
    private convertItemFromServer(etapa: EtapaMySuffix): EtapaMySuffix {
        const copy: EtapaMySuffix = Object.assign({}, etapa);
        return copy;
    }

    /**
     * Convert a EtapaMySuffix to a JSON which can be sent to the server.
     */
    private convert(etapa: EtapaMySuffix): EtapaMySuffix {
        const copy: EtapaMySuffix = Object.assign({}, etapa);
        return copy;
    }
}
