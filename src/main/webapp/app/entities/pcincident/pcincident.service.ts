import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPcincident } from 'app/shared/model/pcincident.model';

type EntityResponseType = HttpResponse<IPcincident>;
type EntityArrayResponseType = HttpResponse<IPcincident[]>;

@Injectable({ providedIn: 'root' })
export class PcincidentService {
    private resourceUrl = SERVER_API_URL + 'api/pcincidents';

    constructor(private http: HttpClient) {}

    create(pcincident: IPcincident): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pcincident);
        return this.http
            .post<IPcincident>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(pcincident: IPcincident): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pcincident);
        return this.http
            .put<IPcincident>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPcincident>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPcincident[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(pcincident: IPcincident): IPcincident {
        const copy: IPcincident = Object.assign({}, pcincident, {
            date_recieved:
                pcincident.date_recieved != null && pcincident.date_recieved.isValid()
                    ? pcincident.date_recieved.format(DATE_FORMAT)
                    : null,
            release_date:
                pcincident.release_date != null && pcincident.release_date.isValid() ? pcincident.release_date.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date_recieved = res.body.date_recieved != null ? moment(res.body.date_recieved) : null;
        res.body.release_date = res.body.release_date != null ? moment(res.body.release_date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((pcincident: IPcincident) => {
            pcincident.date_recieved = pcincident.date_recieved != null ? moment(pcincident.date_recieved) : null;
            pcincident.release_date = pcincident.release_date != null ? moment(pcincident.release_date) : null;
        });
        return res;
    }
}
