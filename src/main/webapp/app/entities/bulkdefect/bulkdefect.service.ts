import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBulkdefect } from 'app/shared/model/bulkdefect.model';

type EntityResponseType = HttpResponse<IBulkdefect>;
type EntityArrayResponseType = HttpResponse<IBulkdefect[]>;

@Injectable({ providedIn: 'root' })
export class BulkdefectService {
    private resourceUrl = SERVER_API_URL + 'api/bulkdefects';

    constructor(private http: HttpClient) {}

    create(bulkdefect: IBulkdefect): Observable<EntityResponseType> {
        return this.http.post<IBulkdefect>(this.resourceUrl, bulkdefect, { observe: 'response' });
    }

    update(bulkdefect: IBulkdefect): Observable<EntityResponseType> {
        return this.http.put<IBulkdefect>(this.resourceUrl, bulkdefect, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBulkdefect>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBulkdefect[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
