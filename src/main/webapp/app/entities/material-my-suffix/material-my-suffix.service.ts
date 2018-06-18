import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { MaterialMySuffix } from './material-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MaterialMySuffix>;

@Injectable()
export class MaterialMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/materials';

    constructor(private http: HttpClient) { }

    create(material: MaterialMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(material);
        return this.http.post<MaterialMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(material: MaterialMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(material);
        return this.http.put<MaterialMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MaterialMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MaterialMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<MaterialMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MaterialMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MaterialMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MaterialMySuffix[]>): HttpResponse<MaterialMySuffix[]> {
        const jsonResponse: MaterialMySuffix[] = res.body;
        const body: MaterialMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MaterialMySuffix.
     */
    private convertItemFromServer(material: MaterialMySuffix): MaterialMySuffix {
        const copy: MaterialMySuffix = Object.assign({}, material);
        return copy;
    }

    /**
     * Convert a MaterialMySuffix to a JSON which can be sent to the server.
     */
    private convert(material: MaterialMySuffix): MaterialMySuffix {
        const copy: MaterialMySuffix = Object.assign({}, material);
        return copy;
    }
}
