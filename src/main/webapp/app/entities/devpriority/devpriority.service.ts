import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDevpriority } from 'app/shared/model/devpriority.model';

type EntityResponseType = HttpResponse<IDevpriority>;
type EntityArrayResponseType = HttpResponse<IDevpriority[]>;

@Injectable({ providedIn: 'root' })
export class DevpriorityService {
    private resourceUrl = SERVER_API_URL + 'api/devpriorities';

    constructor(private http: HttpClient) {}

    create(devpriority: IDevpriority): Observable<EntityResponseType> {
        return this.http.post<IDevpriority>(this.resourceUrl, devpriority, { observe: 'response' });
    }

    update(devpriority: IDevpriority): Observable<EntityResponseType> {
        return this.http.put<IDevpriority>(this.resourceUrl, devpriority, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDevpriority>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDevpriority[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
