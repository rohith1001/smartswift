import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMonthlyreport } from 'app/shared/model/monthlyreport.model';

type EntityResponseType = HttpResponse<IMonthlyreport>;
type EntityArrayResponseType = HttpResponse<IMonthlyreport[]>;

@Injectable({ providedIn: 'root' })
export class MonthlyreportService {
    private resourceUrl = SERVER_API_URL + 'api/monthlyreports';

    constructor(private http: HttpClient) {}

    create(monthlyreport: IMonthlyreport): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(monthlyreport);
        return this.http
            .post<IMonthlyreport>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(monthlyreport: IMonthlyreport): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(monthlyreport);
        return this.http
            .put<IMonthlyreport>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMonthlyreport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMonthlyreport[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(monthlyreport: IMonthlyreport): IMonthlyreport {
        const copy: IMonthlyreport = Object.assign({}, monthlyreport, {
            from_date:
                monthlyreport.from_date != null && monthlyreport.from_date.isValid() ? monthlyreport.from_date.format(DATE_FORMAT) : null,
            to_date: monthlyreport.to_date != null && monthlyreport.to_date.isValid() ? monthlyreport.to_date.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.from_date = res.body.from_date != null ? moment(res.body.from_date) : null;
        res.body.to_date = res.body.to_date != null ? moment(res.body.to_date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((monthlyreport: IMonthlyreport) => {
            monthlyreport.from_date = monthlyreport.from_date != null ? moment(monthlyreport.from_date) : null;
            monthlyreport.to_date = monthlyreport.to_date != null ? moment(monthlyreport.to_date) : null;
        });
        return res;
    }
}
