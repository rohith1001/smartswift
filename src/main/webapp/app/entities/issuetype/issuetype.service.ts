import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIssuetype } from 'app/shared/model/issuetype.model';

type EntityResponseType = HttpResponse<IIssuetype>;
type EntityArrayResponseType = HttpResponse<IIssuetype[]>;

@Injectable({ providedIn: 'root' })
export class IssuetypeService {
    private resourceUrl = SERVER_API_URL + 'api/issuetypes';

    constructor(private http: HttpClient) {}

    create(issuetype: IIssuetype): Observable<EntityResponseType> {
        return this.http.post<IIssuetype>(this.resourceUrl, issuetype, { observe: 'response' });
    }

    update(issuetype: IIssuetype): Observable<EntityResponseType> {
        return this.http.put<IIssuetype>(this.resourceUrl, issuetype, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IIssuetype>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIssuetype[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
