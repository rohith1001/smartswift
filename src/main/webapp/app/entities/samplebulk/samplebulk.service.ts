import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISamplebulk } from 'app/shared/model/samplebulk.model';

type EntityResponseType = HttpResponse<ISamplebulk>;
type EntityArrayResponseType = HttpResponse<ISamplebulk[]>;

@Injectable({ providedIn: 'root' })
export class SamplebulkService {
    private resourceUrl = SERVER_API_URL + 'api/samplebulks';

    constructor(private http: HttpClient) {}

    create(samplebulk: ISamplebulk): Observable<EntityResponseType> {
        return this.http.post<ISamplebulk>(this.resourceUrl, samplebulk, { observe: 'response' });
    }

    update(samplebulk: ISamplebulk): Observable<EntityResponseType> {
        return this.http.put<ISamplebulk>(this.resourceUrl, samplebulk, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISamplebulk>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISamplebulk[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
