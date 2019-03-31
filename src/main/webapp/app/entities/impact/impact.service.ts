import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IImpact } from 'app/shared/model/impact.model';

type EntityResponseType = HttpResponse<IImpact>;
type EntityArrayResponseType = HttpResponse<IImpact[]>;

@Injectable({ providedIn: 'root' })
export class ImpactService {
    private resourceUrl = SERVER_API_URL + 'api/impacts';

    constructor(private http: HttpClient) {}

    create(impact: IImpact): Observable<EntityResponseType> {
        return this.http.post<IImpact>(this.resourceUrl, impact, { observe: 'response' });
    }

    update(impact: IImpact): Observable<EntityResponseType> {
        return this.http.put<IImpact>(this.resourceUrl, impact, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IImpact>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IImpact[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
