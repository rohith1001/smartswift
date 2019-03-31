import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDevservice } from 'app/shared/model/devservice.model';

type EntityResponseType = HttpResponse<IDevservice>;
type EntityArrayResponseType = HttpResponse<IDevservice[]>;

@Injectable({ providedIn: 'root' })
export class DevserviceService {
    private resourceUrl = SERVER_API_URL + 'api/devservices';

    constructor(private http: HttpClient) {}

    create(devservice: IDevservice): Observable<EntityResponseType> {
        return this.http.post<IDevservice>(this.resourceUrl, devservice, { observe: 'response' });
    }

    update(devservice: IDevservice): Observable<EntityResponseType> {
        return this.http.put<IDevservice>(this.resourceUrl, devservice, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDevservice>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDevservice[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
