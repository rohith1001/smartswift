import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOptions } from 'app/shared/model/options.model';

type EntityResponseType = HttpResponse<IOptions>;
type EntityArrayResponseType = HttpResponse<IOptions[]>;

@Injectable({ providedIn: 'root' })
export class OptionsService {
    private resourceUrl = SERVER_API_URL + 'api/options';

    constructor(private http: HttpClient) {}

    create(options: IOptions): Observable<EntityResponseType> {
        return this.http.post<IOptions>(this.resourceUrl, options, { observe: 'response' });
    }

    update(options: IOptions): Observable<EntityResponseType> {
        return this.http.put<IOptions>(this.resourceUrl, options, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOptions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOptions[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
