import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDevelopment_tracker } from 'app/shared/model/development-tracker.model';

type EntityResponseType = HttpResponse<IDevelopment_tracker>;
type EntityArrayResponseType = HttpResponse<IDevelopment_tracker[]>;

@Injectable({ providedIn: 'root' })
export class Development_trackerService {
    private resourceUrl = SERVER_API_URL + 'api/development-trackers';

    constructor(private http: HttpClient) {}

    create(development_tracker: IDevelopment_tracker): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(development_tracker);
        return this.http
            .post<IDevelopment_tracker>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(development_tracker: IDevelopment_tracker): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(development_tracker);
        return this.http
            .put<IDevelopment_tracker>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDevelopment_tracker>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDevelopment_tracker[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(development_tracker: IDevelopment_tracker): IDevelopment_tracker {
        const copy: IDevelopment_tracker = Object.assign({}, development_tracker, {
            request_date:
                development_tracker.request_date != null && development_tracker.request_date.isValid()
                    ? development_tracker.request_date.toJSON()
                    : null,
            ack_date:
                development_tracker.ack_date != null && development_tracker.ack_date.isValid()
                    ? development_tracker.ack_date.toJSON()
                    : null,
            cost_ready_date_actual:
                development_tracker.cost_ready_date_actual != null && development_tracker.cost_ready_date_actual.isValid()
                    ? development_tracker.cost_ready_date_actual.toJSON()
                    : null,
            delivery_to_test_planned:
                development_tracker.delivery_to_test_planned != null && development_tracker.delivery_to_test_planned.isValid()
                    ? development_tracker.delivery_to_test_planned.toJSON()
                    : null,
            delivery_to_test_actual:
                development_tracker.delivery_to_test_actual != null && development_tracker.delivery_to_test_actual.isValid()
                    ? development_tracker.delivery_to_test_actual.toJSON()
                    : null,
            delivery_to_production_planned:
                development_tracker.delivery_to_production_planned != null && development_tracker.delivery_to_production_planned.isValid()
                    ? development_tracker.delivery_to_production_planned.toJSON()
                    : null,
            delivery_to_production_actual:
                development_tracker.delivery_to_production_actual != null && development_tracker.delivery_to_production_actual.isValid()
                    ? development_tracker.delivery_to_production_actual.toJSON()
                    : null,
            defect_date_raised:
                development_tracker.defect_date_raised != null && development_tracker.defect_date_raised.isValid()
                    ? development_tracker.defect_date_raised.toJSON()
                    : null,
            modified_time:
                development_tracker.modified_time != null && development_tracker.modified_time.isValid()
                    ? development_tracker.modified_time.toJSON()
                    : null,
            sow_submission_date:
                development_tracker.sow_submission_date != null && development_tracker.sow_submission_date.isValid()
                    ? development_tracker.sow_submission_date.toJSON()
                    : null,
            finaldate:
                development_tracker.finaldate != null && development_tracker.finaldate.isValid()
                    ? development_tracker.finaldate.toJSON()
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.request_date = res.body.request_date != null ? moment(res.body.request_date) : null;
        res.body.ack_date = res.body.ack_date != null ? moment(res.body.ack_date) : null;
        res.body.cost_ready_date_actual = res.body.cost_ready_date_actual != null ? moment(res.body.cost_ready_date_actual) : null;
        res.body.delivery_to_test_planned = res.body.delivery_to_test_planned != null ? moment(res.body.delivery_to_test_planned) : null;
        res.body.delivery_to_test_actual = res.body.delivery_to_test_actual != null ? moment(res.body.delivery_to_test_actual) : null;
        res.body.delivery_to_production_planned =
            res.body.delivery_to_production_planned != null ? moment(res.body.delivery_to_production_planned) : null;
        res.body.delivery_to_production_actual =
            res.body.delivery_to_production_actual != null ? moment(res.body.delivery_to_production_actual) : null;
        res.body.defect_date_raised = res.body.defect_date_raised != null ? moment(res.body.defect_date_raised) : null;
        res.body.modified_time = res.body.modified_time != null ? moment(res.body.modified_time) : null;
        res.body.sow_submission_date = res.body.sow_submission_date != null ? moment(res.body.sow_submission_date) : null;
        res.body.finaldate = res.body.finaldate != null ? moment(res.body.finaldate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((development_tracker: IDevelopment_tracker) => {
            development_tracker.request_date = development_tracker.request_date != null ? moment(development_tracker.request_date) : null;
            development_tracker.ack_date = development_tracker.ack_date != null ? moment(development_tracker.ack_date) : null;
            development_tracker.cost_ready_date_actual =
                development_tracker.cost_ready_date_actual != null ? moment(development_tracker.cost_ready_date_actual) : null;
            development_tracker.delivery_to_test_planned =
                development_tracker.delivery_to_test_planned != null ? moment(development_tracker.delivery_to_test_planned) : null;
            development_tracker.delivery_to_test_actual =
                development_tracker.delivery_to_test_actual != null ? moment(development_tracker.delivery_to_test_actual) : null;
            development_tracker.delivery_to_production_planned =
                development_tracker.delivery_to_production_planned != null
                    ? moment(development_tracker.delivery_to_production_planned)
                    : null;
            development_tracker.delivery_to_production_actual =
                development_tracker.delivery_to_production_actual != null
                    ? moment(development_tracker.delivery_to_production_actual)
                    : null;
            development_tracker.defect_date_raised =
                development_tracker.defect_date_raised != null ? moment(development_tracker.defect_date_raised) : null;
            development_tracker.modified_time =
                development_tracker.modified_time != null ? moment(development_tracker.modified_time) : null;
            development_tracker.sow_submission_date =
                development_tracker.sow_submission_date != null ? moment(development_tracker.sow_submission_date) : null;
            development_tracker.finaldate = development_tracker.finaldate != null ? moment(development_tracker.finaldate) : null;
        });
        return res;
    }
}
