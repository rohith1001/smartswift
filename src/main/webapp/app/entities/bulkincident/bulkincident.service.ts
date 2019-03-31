import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBulkincident } from 'app/shared/model/bulkincident.model';

type EntityResponseType = HttpResponse<IBulkincident>;
type EntityArrayResponseType = HttpResponse<IBulkincident[]>;

@Injectable({ providedIn: 'root' })
export class BulkincidentService {
    private resourceUrl = SERVER_API_URL + 'api/bulkincidents';

    constructor(private http: HttpClient) {}

    create(bulkincident: IBulkincident): Observable<EntityResponseType> {
        return this.http.post<IBulkincident>(this.resourceUrl, bulkincident, { observe: 'response' });
    }

    update(bulkincident: IBulkincident): Observable<EntityResponseType> {
        return this.http.put<IBulkincident>(this.resourceUrl, bulkincident, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBulkincident>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBulkincident[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
