import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBulkrelease } from 'app/shared/model/bulkrelease.model';

type EntityResponseType = HttpResponse<IBulkrelease>;
type EntityArrayResponseType = HttpResponse<IBulkrelease[]>;

@Injectable({ providedIn: 'root' })
export class BulkreleaseService {
    private resourceUrl = SERVER_API_URL + 'api/bulkreleases';

    constructor(private http: HttpClient) {}

    create(bulkrelease: IBulkrelease): Observable<EntityResponseType> {
        return this.http.post<IBulkrelease>(this.resourceUrl, bulkrelease, { observe: 'response' });
    }

    update(bulkrelease: IBulkrelease): Observable<EntityResponseType> {
        return this.http.put<IBulkrelease>(this.resourceUrl, bulkrelease, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBulkrelease>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBulkrelease[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
