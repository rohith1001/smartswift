import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPcdefect } from 'app/shared/model/pcdefect.model';

type EntityResponseType = HttpResponse<IPcdefect>;
type EntityArrayResponseType = HttpResponse<IPcdefect[]>;

@Injectable({ providedIn: 'root' })
export class PcdefectService {
    private resourceUrl = SERVER_API_URL + 'api/pcdefects';

    constructor(private http: HttpClient) {}

    create(pcdefect: IPcdefect): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pcdefect);
        return this.http
            .post<IPcdefect>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(pcdefect: IPcdefect): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pcdefect);
        return this.http
            .put<IPcdefect>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPcdefect>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPcdefect[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(pcdefect: IPcdefect): IPcdefect {
        const copy: IPcdefect = Object.assign({}, pcdefect, {
            date_raised: pcdefect.date_raised != null && pcdefect.date_raised.isValid() ? pcdefect.date_raised.format(DATE_FORMAT) : null,
            date_closed: pcdefect.date_closed != null && pcdefect.date_closed.isValid() ? pcdefect.date_closed.format(DATE_FORMAT) : null,
            release_date:
                pcdefect.release_date != null && pcdefect.release_date.isValid() ? pcdefect.release_date.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date_raised = res.body.date_raised != null ? moment(res.body.date_raised) : null;
        res.body.date_closed = res.body.date_closed != null ? moment(res.body.date_closed) : null;
        res.body.release_date = res.body.release_date != null ? moment(res.body.release_date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((pcdefect: IPcdefect) => {
            pcdefect.date_raised = pcdefect.date_raised != null ? moment(pcdefect.date_raised) : null;
            pcdefect.date_closed = pcdefect.date_closed != null ? moment(pcdefect.date_closed) : null;
            pcdefect.release_date = pcdefect.release_date != null ? moment(pcdefect.release_date) : null;
        });
        return res;
    }
}
