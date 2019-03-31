import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReports } from 'app/shared/model/reports.model';

type EntityResponseType = HttpResponse<IReports>;
type EntityArrayResponseType = HttpResponse<IReports[]>;

@Injectable({ providedIn: 'root' })
export class ReportsService {
    private resourceUrl = SERVER_API_URL + 'api/reports';

    constructor(private http: HttpClient) {}

    create(reports: IReports): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(reports);
        return this.http
            .post<IReports>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(reports: IReports): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(reports);
        return this.http
            .put<IReports>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IReports>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IReports[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(reports: IReports): IReports {
        const copy: IReports = Object.assign({}, reports, {
            generated_on: reports.generated_on != null && reports.generated_on.isValid() ? reports.generated_on.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.generated_on = res.body.generated_on != null ? moment(res.body.generated_on) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((reports: IReports) => {
            reports.generated_on = reports.generated_on != null ? moment(reports.generated_on) : null;
        });
        return res;
    }
}
