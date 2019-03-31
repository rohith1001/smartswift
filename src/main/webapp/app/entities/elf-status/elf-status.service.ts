import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IElf_status } from 'app/shared/model/elf-status.model';

type EntityResponseType = HttpResponse<IElf_status>;
type EntityArrayResponseType = HttpResponse<IElf_status[]>;

@Injectable({ providedIn: 'root' })
export class Elf_statusService {
    private resourceUrl = SERVER_API_URL + 'api/elf-statuses';

    constructor(private http: HttpClient) {}

    create(elf_status: IElf_status): Observable<EntityResponseType> {
        return this.http.post<IElf_status>(this.resourceUrl, elf_status, { observe: 'response' });
    }

    update(elf_status: IElf_status): Observable<EntityResponseType> {
        return this.http.put<IElf_status>(this.resourceUrl, elf_status, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IElf_status>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IElf_status[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
