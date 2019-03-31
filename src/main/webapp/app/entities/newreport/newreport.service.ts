import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INewreport } from 'app/shared/model/newreport.model';

type EntityResponseType = HttpResponse<INewreport>;
type EntityArrayResponseType = HttpResponse<INewreport[]>;

@Injectable({ providedIn: 'root' })
export class NewreportService {
    private resourceUrl = SERVER_API_URL + 'api/newreports';

    constructor(private http: HttpClient) {}

    create(newreport: INewreport): Observable<EntityResponseType> {
        return this.http.post<INewreport>(this.resourceUrl, newreport, { observe: 'response' });
    }

    update(newreport: INewreport): Observable<EntityResponseType> {
        return this.http.put<INewreport>(this.resourceUrl, newreport, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<INewreport>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<INewreport[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
