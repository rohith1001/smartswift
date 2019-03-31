import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IConfigslas } from 'app/shared/model/configslas.model';

type EntityResponseType = HttpResponse<IConfigslas>;
type EntityArrayResponseType = HttpResponse<IConfigslas[]>;

@Injectable({ providedIn: 'root' })
export class ConfigslasService {
    private resourceUrl = SERVER_API_URL + 'api/configslas';

    constructor(private http: HttpClient) {}

    create(configslas: IConfigslas): Observable<EntityResponseType> {
        return this.http.post<IConfigslas>(this.resourceUrl, configslas, { observe: 'response' });
    }

    update(configslas: IConfigslas): Observable<EntityResponseType> {
        return this.http.put<IConfigslas>(this.resourceUrl, configslas, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IConfigslas>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IConfigslas[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
