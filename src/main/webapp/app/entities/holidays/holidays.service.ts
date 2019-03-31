import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHolidays } from 'app/shared/model/holidays.model';

type EntityResponseType = HttpResponse<IHolidays>;
type EntityArrayResponseType = HttpResponse<IHolidays[]>;

@Injectable({ providedIn: 'root' })
export class HolidaysService {
    private resourceUrl = SERVER_API_URL + 'api/holidays';

    constructor(private http: HttpClient) {}

    create(holidays: IHolidays): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(holidays);
        return this.http
            .post<IHolidays>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(holidays: IHolidays): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(holidays);
        return this.http
            .put<IHolidays>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IHolidays>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHolidays[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(holidays: IHolidays): IHolidays {
        const copy: IHolidays = Object.assign({}, holidays, {
            holiday_date:
                holidays.holiday_date != null && holidays.holiday_date.isValid() ? holidays.holiday_date.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.holiday_date = res.body.holiday_date != null ? moment(res.body.holiday_date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((holidays: IHolidays) => {
            holidays.holiday_date = holidays.holiday_date != null ? moment(holidays.holiday_date) : null;
        });
        return res;
    }
}
