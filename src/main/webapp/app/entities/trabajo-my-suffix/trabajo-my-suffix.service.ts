import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TrabajoMySuffix } from './trabajo-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TrabajoMySuffix>;

@Injectable()
export class TrabajoMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/trabajos';

    constructor(private http: HttpClient) { }

    create(trabajo: TrabajoMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(trabajo);
        return this.http.post<TrabajoMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(trabajo: TrabajoMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(trabajo);
        return this.http.put<TrabajoMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TrabajoMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TrabajoMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<TrabajoMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TrabajoMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TrabajoMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TrabajoMySuffix[]>): HttpResponse<TrabajoMySuffix[]> {
        const jsonResponse: TrabajoMySuffix[] = res.body;
        const body: TrabajoMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TrabajoMySuffix.
     */
    private convertItemFromServer(trabajo: TrabajoMySuffix): TrabajoMySuffix {
        const copy: TrabajoMySuffix = Object.assign({}, trabajo);
        return copy;
    }

    /**
     * Convert a TrabajoMySuffix to a JSON which can be sent to the server.
     */
    private convert(trabajo: TrabajoMySuffix): TrabajoMySuffix {
        const copy: TrabajoMySuffix = Object.assign({}, trabajo);
        return copy;
    }
}
