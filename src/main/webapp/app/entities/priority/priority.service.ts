import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPriority } from 'app/shared/model/priority.model';

type EntityResponseType = HttpResponse<IPriority>;
type EntityArrayResponseType = HttpResponse<IPriority[]>;

@Injectable({ providedIn: 'root' })
export class PriorityService {
    private resourceUrl = SERVER_API_URL + 'api/priorities';

    constructor(private http: HttpClient) {}

    create(priority: IPriority): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(priority);
        return this.http
            .post<IPriority>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(priority: IPriority): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(priority);
        return this.http
            .put<IPriority>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPriority>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPriority[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(priority: IPriority): IPriority {
        const copy: IPriority = Object.assign({}, priority, {
            ack_time: priority.ack_time != null && priority.ack_time.isValid() ? priority.ack_time.format(DATE_FORMAT) : null,
            restore_time:
                priority.restore_time != null && priority.restore_time.isValid() ? priority.restore_time.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.ack_time = res.body.ack_time != null ? moment(res.body.ack_time) : null;
        res.body.restore_time = res.body.restore_time != null ? moment(res.body.restore_time) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((priority: IPriority) => {
            priority.ack_time = priority.ack_time != null ? moment(priority.ack_time) : null;
            priority.restore_time = priority.restore_time != null ? moment(priority.restore_time) : null;
        });
        return res;
    }
}
