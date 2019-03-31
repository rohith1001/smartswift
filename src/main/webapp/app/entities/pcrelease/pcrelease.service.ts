import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPcrelease } from 'app/shared/model/pcrelease.model';

type EntityResponseType = HttpResponse<IPcrelease>;
type EntityArrayResponseType = HttpResponse<IPcrelease[]>;

@Injectable({ providedIn: 'root' })
export class PcreleaseService {
    private resourceUrl = SERVER_API_URL + 'api/pcreleases';

    constructor(private http: HttpClient) {}

    create(pcrelease: IPcrelease): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pcrelease);
        return this.http
            .post<IPcrelease>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(pcrelease: IPcrelease): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pcrelease);
        return this.http
            .put<IPcrelease>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPcrelease>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPcrelease[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(pcrelease: IPcrelease): IPcrelease {
        const copy: IPcrelease = Object.assign({}, pcrelease, {
            release_date:
                pcrelease.release_date != null && pcrelease.release_date.isValid() ? pcrelease.release_date.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.release_date = res.body.release_date != null ? moment(res.body.release_date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((pcrelease: IPcrelease) => {
            pcrelease.release_date = pcrelease.release_date != null ? moment(pcrelease.release_date) : null;
        });
        return res;
    }
}
