import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResolved } from 'app/shared/model/resolved.model';

type EntityResponseType = HttpResponse<IResolved>;
type EntityArrayResponseType = HttpResponse<IResolved[]>;

@Injectable({ providedIn: 'root' })
export class ResolvedService {
    private resourceUrl = SERVER_API_URL + 'api/resolveds';

    constructor(private http: HttpClient) {}

    create(resolved: IResolved): Observable<EntityResponseType> {
        return this.http.post<IResolved>(this.resourceUrl, resolved, { observe: 'response' });
    }

    update(resolved: IResolved): Observable<EntityResponseType> {
        return this.http.put<IResolved>(this.resourceUrl, resolved, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IResolved>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IResolved[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
