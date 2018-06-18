import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AnomaliaMySuffix } from './anomalia-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AnomaliaMySuffix>;

@Injectable()
export class AnomaliaMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/anomalias';

    constructor(private http: HttpClient) { }

    create(anomalia: AnomaliaMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(anomalia);
        return this.http.post<AnomaliaMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(anomalia: AnomaliaMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(anomalia);
        return this.http.put<AnomaliaMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AnomaliaMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AnomaliaMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<AnomaliaMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AnomaliaMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AnomaliaMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AnomaliaMySuffix[]>): HttpResponse<AnomaliaMySuffix[]> {
        const jsonResponse: AnomaliaMySuffix[] = res.body;
        const body: AnomaliaMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AnomaliaMySuffix.
     */
    private convertItemFromServer(anomalia: AnomaliaMySuffix): AnomaliaMySuffix {
        const copy: AnomaliaMySuffix = Object.assign({}, anomalia);
        return copy;
    }

    /**
     * Convert a AnomaliaMySuffix to a JSON which can be sent to the server.
     */
    private convert(anomalia: AnomaliaMySuffix): AnomaliaMySuffix {
        const copy: AnomaliaMySuffix = Object.assign({}, anomalia);
        return copy;
    }
}
