import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHld } from 'app/shared/model/hld.model';

type EntityResponseType = HttpResponse<IHld>;
type EntityArrayResponseType = HttpResponse<IHld[]>;

@Injectable({ providedIn: 'root' })
export class HldService {
    private resourceUrl = SERVER_API_URL + 'api/hlds';

    constructor(private http: HttpClient) {}

    create(hld: IHld): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(hld);
        return this.http
            .post<IHld>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(hld: IHld): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(hld);
        return this.http
            .put<IHld>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IHld>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHld[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(hld: IHld): IHld {
        const copy: IHld = Object.assign({}, hld, {
            request_date: hld.request_date != null && hld.request_date.isValid() ? hld.request_date.toJSON() : null,
            actual_acknowledgement_date:
                hld.actual_acknowledgement_date != null && hld.actual_acknowledgement_date.isValid()
                    ? hld.actual_acknowledgement_date.toJSON()
                    : null,
            delivery_date_planned:
                hld.delivery_date_planned != null && hld.delivery_date_planned.isValid() ? hld.delivery_date_planned.toJSON() : null,
            delivery_date_actual:
                hld.delivery_date_actual != null && hld.delivery_date_actual.isValid() ? hld.delivery_date_actual.toJSON() : null,
            agreed_date: hld.agreed_date != null && hld.agreed_date.isValid() ? hld.agreed_date.toJSON() : null,
            hold_date: hld.hold_date != null && hld.hold_date.isValid() ? hld.hold_date.toJSON() : null,
            modified_time: hld.modified_time != null && hld.modified_time.isValid() ? hld.modified_time.toJSON() : null,
            wif_submission_date:
                hld.wif_submission_date != null && hld.wif_submission_date.isValid() ? hld.wif_submission_date.toJSON() : null,
            finaldate: hld.finaldate != null && hld.finaldate.isValid() ? hld.finaldate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.request_date = res.body.request_date != null ? moment(res.body.request_date) : null;
        res.body.actual_acknowledgement_date =
            res.body.actual_acknowledgement_date != null ? moment(res.body.actual_acknowledgement_date) : null;
        res.body.delivery_date_planned = res.body.delivery_date_planned != null ? moment(res.body.delivery_date_planned) : null;
        res.body.delivery_date_actual = res.body.delivery_date_actual != null ? moment(res.body.delivery_date_actual) : null;
        res.body.agreed_date = res.body.agreed_date != null ? moment(res.body.agreed_date) : null;
        res.body.hold_date = res.body.hold_date != null ? moment(res.body.hold_date) : null;
        res.body.modified_time = res.body.modified_time != null ? moment(res.body.modified_time) : null;
        res.body.wif_submission_date = res.body.wif_submission_date != null ? moment(res.body.wif_submission_date) : null;
        res.body.finaldate = res.body.finaldate != null ? moment(res.body.finaldate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((hld: IHld) => {
            hld.request_date = hld.request_date != null ? moment(hld.request_date) : null;
            hld.actual_acknowledgement_date = hld.actual_acknowledgement_date != null ? moment(hld.actual_acknowledgement_date) : null;
            hld.delivery_date_planned = hld.delivery_date_planned != null ? moment(hld.delivery_date_planned) : null;
            hld.delivery_date_actual = hld.delivery_date_actual != null ? moment(hld.delivery_date_actual) : null;
            hld.agreed_date = hld.agreed_date != null ? moment(hld.agreed_date) : null;
            hld.hold_date = hld.hold_date != null ? moment(hld.hold_date) : null;
            hld.modified_time = hld.modified_time != null ? moment(hld.modified_time) : null;
            hld.wif_submission_date = hld.wif_submission_date != null ? moment(hld.wif_submission_date) : null;
            hld.finaldate = hld.finaldate != null ? moment(hld.finaldate) : null;
        });
        return res;
    }
}
