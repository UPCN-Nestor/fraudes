import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { InspeccionMySuffix } from './inspeccion-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<InspeccionMySuffix>;

@Injectable()
export class InspeccionMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/inspeccions';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(inspeccion: InspeccionMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(inspeccion);
        return this.http.post<InspeccionMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(inspeccion: InspeccionMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(inspeccion);
        return this.http.put<InspeccionMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<InspeccionMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<InspeccionMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<InspeccionMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<InspeccionMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: InspeccionMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<InspeccionMySuffix[]>): HttpResponse<InspeccionMySuffix[]> {
        const jsonResponse: InspeccionMySuffix[] = res.body;
        const body: InspeccionMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to InspeccionMySuffix.
     */
    private convertItemFromServer(inspeccion: InspeccionMySuffix): InspeccionMySuffix {
        const copy: InspeccionMySuffix = Object.assign({}, inspeccion);
        copy.fechahora = this.dateUtils
            .convertDateTimeFromServer(inspeccion.fechahora);
        copy.fechaToma = this.dateUtils
            .convertLocalDateFromServer(inspeccion.fechaToma);
        return copy;
    }

    /**
     * Convert a InspeccionMySuffix to a JSON which can be sent to the server.
     */
    private convert(inspeccion: InspeccionMySuffix): InspeccionMySuffix {
        const copy: InspeccionMySuffix = Object.assign({}, inspeccion);

        copy.fechahora = this.dateUtils.toDate(inspeccion.fechahora);
        copy.fechaToma = this.dateUtils
            .convertLocalDateToServer(inspeccion.fechaToma);
        return copy;
    }
}
