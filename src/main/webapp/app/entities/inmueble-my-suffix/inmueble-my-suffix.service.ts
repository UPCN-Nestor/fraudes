import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { InmuebleMySuffix } from './inmueble-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<InmuebleMySuffix>;

@Injectable()
export class InmuebleMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/inmuebles';

    constructor(private http: HttpClient) { }

    create(inmueble: InmuebleMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(inmueble);
        return this.http.post<InmuebleMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(inmueble: InmuebleMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(inmueble);
        return this.http.put<InmuebleMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<InmuebleMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<InmuebleMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<InmuebleMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<InmuebleMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: InmuebleMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<InmuebleMySuffix[]>): HttpResponse<InmuebleMySuffix[]> {
        const jsonResponse: InmuebleMySuffix[] = res.body;
        const body: InmuebleMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to InmuebleMySuffix.
     */
    private convertItemFromServer(inmueble: InmuebleMySuffix): InmuebleMySuffix {
        const copy: InmuebleMySuffix = Object.assign({}, inmueble);
        return copy;
    }

    /**
     * Convert a InmuebleMySuffix to a JSON which can be sent to the server.
     */
    private convert(inmueble: InmuebleMySuffix): InmuebleMySuffix {
        const copy: InmuebleMySuffix = Object.assign({}, inmueble);
        return copy;
    }
}
