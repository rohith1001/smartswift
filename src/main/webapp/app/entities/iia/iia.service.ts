import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIia } from 'app/shared/model/iia.model';

type EntityResponseType = HttpResponse<IIia>;
type EntityArrayResponseType = HttpResponse<IIia[]>;

@Injectable({ providedIn: 'root' })
export class IiaService {
    private resourceUrl = SERVER_API_URL + 'api/iias';

    constructor(private http: HttpClient) {}

    create(iia: IIia): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(iia);
        return this.http
            .post<IIia>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(iia: IIia): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(iia);
        return this.http
            .put<IIia>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IIia>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IIia[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(iia: IIia): IIia {
        const copy: IIia = Object.assign({}, iia, {
            request_date: iia.request_date != null && iia.request_date.isValid() ? iia.request_date.toJSON() : null,
            actual_acknowledgement_date:
                iia.actual_acknowledgement_date != null && iia.actual_acknowledgement_date.isValid()
                    ? iia.actual_acknowledgement_date.toJSON()
                    : null,
            delivery_date_planned:
                iia.delivery_date_planned != null && iia.delivery_date_planned.isValid() ? iia.delivery_date_planned.toJSON() : null,
            delivery_date_actual:
                iia.delivery_date_actual != null && iia.delivery_date_actual.isValid() ? iia.delivery_date_actual.toJSON() : null,
            agreed_date: iia.agreed_date != null && iia.agreed_date.isValid() ? iia.agreed_date.toJSON() : null,
            iia_resubmitted_date:
                iia.iia_resubmitted_date != null && iia.iia_resubmitted_date.isValid() ? iia.iia_resubmitted_date.toJSON() : null,
            hold_date: iia.hold_date != null && iia.hold_date.isValid() ? iia.hold_date.toJSON() : null,
            modified_time: iia.modified_time != null && iia.modified_time.isValid() ? iia.modified_time.toJSON() : null,
            finaldate: iia.finaldate != null && iia.finaldate.isValid() ? iia.finaldate.toJSON() : null,
            resubmission_request_date:
                iia.resubmission_request_date != null && iia.resubmission_request_date.isValid()
                    ? iia.resubmission_request_date.toJSON()
                    : null
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
        res.body.iia_resubmitted_date = res.body.iia_resubmitted_date != null ? moment(res.body.iia_resubmitted_date) : null;
        res.body.hold_date = res.body.hold_date != null ? moment(res.body.hold_date) : null;
        res.body.modified_time = res.body.modified_time != null ? moment(res.body.modified_time) : null;
        res.body.finaldate = res.body.finaldate != null ? moment(res.body.finaldate) : null;
        res.body.resubmission_request_date = res.body.resubmission_request_date != null ? moment(res.body.resubmission_request_date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((iia: IIia) => {
            iia.request_date = iia.request_date != null ? moment(iia.request_date) : null;
            iia.actual_acknowledgement_date = iia.actual_acknowledgement_date != null ? moment(iia.actual_acknowledgement_date) : null;
            iia.delivery_date_planned = iia.delivery_date_planned != null ? moment(iia.delivery_date_planned) : null;
            iia.delivery_date_actual = iia.delivery_date_actual != null ? moment(iia.delivery_date_actual) : null;
            iia.agreed_date = iia.agreed_date != null ? moment(iia.agreed_date) : null;
            iia.iia_resubmitted_date = iia.iia_resubmitted_date != null ? moment(iia.iia_resubmitted_date) : null;
            iia.hold_date = iia.hold_date != null ? moment(iia.hold_date) : null;
            iia.modified_time = iia.modified_time != null ? moment(iia.modified_time) : null;
            iia.finaldate = iia.finaldate != null ? moment(iia.finaldate) : null;
            iia.resubmission_request_date = iia.resubmission_request_date != null ? moment(iia.resubmission_request_date) : null;
        });
        return res;
    }
}
