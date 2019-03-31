import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IConfigtype } from 'app/shared/model/configtype.model';

type EntityResponseType = HttpResponse<IConfigtype>;
type EntityArrayResponseType = HttpResponse<IConfigtype[]>;

@Injectable({ providedIn: 'root' })
export class ConfigtypeService {
    private resourceUrl = SERVER_API_URL + 'api/configtypes';

    constructor(private http: HttpClient) {}

    create(configtype: IConfigtype): Observable<EntityResponseType> {
        return this.http.post<IConfigtype>(this.resourceUrl, configtype, { observe: 'response' });
    }

    update(configtype: IConfigtype): Observable<EntityResponseType> {
        return this.http.put<IConfigtype>(this.resourceUrl, configtype, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IConfigtype>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IConfigtype[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
