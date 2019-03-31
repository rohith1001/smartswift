import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPc_tracker } from 'app/shared/model/pc-tracker.model';

type EntityResponseType = HttpResponse<IPc_tracker>;
type EntityArrayResponseType = HttpResponse<IPc_tracker[]>;

@Injectable({ providedIn: 'root' })
export class Pc_trackerService {
    private resourceUrl = SERVER_API_URL + 'api/pc-trackers';

    constructor(private http: HttpClient) {}

    create(pc_tracker: IPc_tracker): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pc_tracker);
        return this.http
            .post<IPc_tracker>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(pc_tracker: IPc_tracker): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pc_tracker);
        return this.http
            .put<IPc_tracker>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPc_tracker>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPc_tracker[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(pc_tracker: IPc_tracker): IPc_tracker {
        const copy: IPc_tracker = Object.assign({}, pc_tracker, {
            date_received:
                pc_tracker.date_received != null && pc_tracker.date_received.isValid() ? pc_tracker.date_received.toJSON() : null,
            iia_delivery_date_planned:
                pc_tracker.iia_delivery_date_planned != null && pc_tracker.iia_delivery_date_planned.isValid()
                    ? pc_tracker.iia_delivery_date_planned.toJSON()
                    : null,
            iia_delivery_date_actual:
                pc_tracker.iia_delivery_date_actual != null && pc_tracker.iia_delivery_date_actual.isValid()
                    ? pc_tracker.iia_delivery_date_actual.toJSON()
                    : null,
            dcd_delivery_date_planned:
                pc_tracker.dcd_delivery_date_planned != null && pc_tracker.dcd_delivery_date_planned.isValid()
                    ? pc_tracker.dcd_delivery_date_planned.toJSON()
                    : null,
            dcd_delivery_date_actual:
                pc_tracker.dcd_delivery_date_actual != null && pc_tracker.dcd_delivery_date_actual.isValid()
                    ? pc_tracker.dcd_delivery_date_actual.toJSON()
                    : null,
            wr_acknowledge_date_planned:
                pc_tracker.wr_acknowledge_date_planned != null && pc_tracker.wr_acknowledge_date_planned.isValid()
                    ? pc_tracker.wr_acknowledge_date_planned.toJSON()
                    : null,
            wr_acknowledge_date_actual:
                pc_tracker.wr_acknowledge_date_actual != null && pc_tracker.wr_acknowledge_date_actual.isValid()
                    ? pc_tracker.wr_acknowledge_date_actual.toJSON()
                    : null,
            wr_costready_date_planned:
                pc_tracker.wr_costready_date_planned != null && pc_tracker.wr_costready_date_planned.isValid()
                    ? pc_tracker.wr_costready_date_planned.toJSON()
                    : null,
            wr_costready_date_actual:
                pc_tracker.wr_costready_date_actual != null && pc_tracker.wr_costready_date_actual.isValid()
                    ? pc_tracker.wr_costready_date_actual.toJSON()
                    : null,
            hlcd_delivery_date_planned:
                pc_tracker.hlcd_delivery_date_planned != null && pc_tracker.hlcd_delivery_date_planned.isValid()
                    ? pc_tracker.hlcd_delivery_date_planned.toJSON()
                    : null,
            hlcd_delivery_date_actual:
                pc_tracker.hlcd_delivery_date_actual != null && pc_tracker.hlcd_delivery_date_actual.isValid()
                    ? pc_tracker.hlcd_delivery_date_actual.toJSON()
                    : null,
            test_ready_date_planned:
                pc_tracker.test_ready_date_planned != null && pc_tracker.test_ready_date_planned.isValid()
                    ? pc_tracker.test_ready_date_planned.toJSON()
                    : null,
            test_ready_date_actual:
                pc_tracker.test_ready_date_actual != null && pc_tracker.test_ready_date_actual.isValid()
                    ? pc_tracker.test_ready_date_actual.toJSON()
                    : null,
            launch_date_planned:
                pc_tracker.launch_date_planned != null && pc_tracker.launch_date_planned.isValid()
                    ? pc_tracker.launch_date_planned.toJSON()
                    : null,
            launch_date_actual:
                pc_tracker.launch_date_actual != null && pc_tracker.launch_date_actual.isValid()
                    ? pc_tracker.launch_date_actual.toJSON()
                    : null,
            delivery_date_planned:
                pc_tracker.delivery_date_planned != null && pc_tracker.delivery_date_planned.isValid()
                    ? pc_tracker.delivery_date_planned.toJSON()
                    : null,
            delivery_date_actual:
                pc_tracker.delivery_date_actual != null && pc_tracker.delivery_date_actual.isValid()
                    ? pc_tracker.delivery_date_actual.toJSON()
                    : null,
            modified_time: pc_tracker.modified_time != null && pc_tracker.modified_time.isValid() ? pc_tracker.modified_time.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date_received = res.body.date_received != null ? moment(res.body.date_received) : null;
        res.body.iia_delivery_date_planned = res.body.iia_delivery_date_planned != null ? moment(res.body.iia_delivery_date_planned) : null;
        res.body.iia_delivery_date_actual = res.body.iia_delivery_date_actual != null ? moment(res.body.iia_delivery_date_actual) : null;
        res.body.dcd_delivery_date_planned = res.body.dcd_delivery_date_planned != null ? moment(res.body.dcd_delivery_date_planned) : null;
        res.body.dcd_delivery_date_actual = res.body.dcd_delivery_date_actual != null ? moment(res.body.dcd_delivery_date_actual) : null;
        res.body.wr_acknowledge_date_planned =
            res.body.wr_acknowledge_date_planned != null ? moment(res.body.wr_acknowledge_date_planned) : null;
        res.body.wr_acknowledge_date_actual =
            res.body.wr_acknowledge_date_actual != null ? moment(res.body.wr_acknowledge_date_actual) : null;
        res.body.wr_costready_date_planned = res.body.wr_costready_date_planned != null ? moment(res.body.wr_costready_date_planned) : null;
        res.body.wr_costready_date_actual = res.body.wr_costready_date_actual != null ? moment(res.body.wr_costready_date_actual) : null;
        res.body.hlcd_delivery_date_planned =
            res.body.hlcd_delivery_date_planned != null ? moment(res.body.hlcd_delivery_date_planned) : null;
        res.body.hlcd_delivery_date_actual = res.body.hlcd_delivery_date_actual != null ? moment(res.body.hlcd_delivery_date_actual) : null;
        res.body.test_ready_date_planned = res.body.test_ready_date_planned != null ? moment(res.body.test_ready_date_planned) : null;
        res.body.test_ready_date_actual = res.body.test_ready_date_actual != null ? moment(res.body.test_ready_date_actual) : null;
        res.body.launch_date_planned = res.body.launch_date_planned != null ? moment(res.body.launch_date_planned) : null;
        res.body.launch_date_actual = res.body.launch_date_actual != null ? moment(res.body.launch_date_actual) : null;
        res.body.delivery_date_planned = res.body.delivery_date_planned != null ? moment(res.body.delivery_date_planned) : null;
        res.body.delivery_date_actual = res.body.delivery_date_actual != null ? moment(res.body.delivery_date_actual) : null;
        res.body.modified_time = res.body.modified_time != null ? moment(res.body.modified_time) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((pc_tracker: IPc_tracker) => {
            pc_tracker.date_received = pc_tracker.date_received != null ? moment(pc_tracker.date_received) : null;
            pc_tracker.iia_delivery_date_planned =
                pc_tracker.iia_delivery_date_planned != null ? moment(pc_tracker.iia_delivery_date_planned) : null;
            pc_tracker.iia_delivery_date_actual =
                pc_tracker.iia_delivery_date_actual != null ? moment(pc_tracker.iia_delivery_date_actual) : null;
            pc_tracker.dcd_delivery_date_planned =
                pc_tracker.dcd_delivery_date_planned != null ? moment(pc_tracker.dcd_delivery_date_planned) : null;
            pc_tracker.dcd_delivery_date_actual =
                pc_tracker.dcd_delivery_date_actual != null ? moment(pc_tracker.dcd_delivery_date_actual) : null;
            pc_tracker.wr_acknowledge_date_planned =
                pc_tracker.wr_acknowledge_date_planned != null ? moment(pc_tracker.wr_acknowledge_date_planned) : null;
            pc_tracker.wr_acknowledge_date_actual =
                pc_tracker.wr_acknowledge_date_actual != null ? moment(pc_tracker.wr_acknowledge_date_actual) : null;
            pc_tracker.wr_costready_date_planned =
                pc_tracker.wr_costready_date_planned != null ? moment(pc_tracker.wr_costready_date_planned) : null;
            pc_tracker.wr_costready_date_actual =
                pc_tracker.wr_costready_date_actual != null ? moment(pc_tracker.wr_costready_date_actual) : null;
            pc_tracker.hlcd_delivery_date_planned =
                pc_tracker.hlcd_delivery_date_planned != null ? moment(pc_tracker.hlcd_delivery_date_planned) : null;
            pc_tracker.hlcd_delivery_date_actual =
                pc_tracker.hlcd_delivery_date_actual != null ? moment(pc_tracker.hlcd_delivery_date_actual) : null;
            pc_tracker.test_ready_date_planned =
                pc_tracker.test_ready_date_planned != null ? moment(pc_tracker.test_ready_date_planned) : null;
            pc_tracker.test_ready_date_actual =
                pc_tracker.test_ready_date_actual != null ? moment(pc_tracker.test_ready_date_actual) : null;
            pc_tracker.launch_date_planned = pc_tracker.launch_date_planned != null ? moment(pc_tracker.launch_date_planned) : null;
            pc_tracker.launch_date_actual = pc_tracker.launch_date_actual != null ? moment(pc_tracker.launch_date_actual) : null;
            pc_tracker.delivery_date_planned = pc_tracker.delivery_date_planned != null ? moment(pc_tracker.delivery_date_planned) : null;
            pc_tracker.delivery_date_actual = pc_tracker.delivery_date_actual != null ? moment(pc_tracker.delivery_date_actual) : null;
            pc_tracker.modified_time = pc_tracker.modified_time != null ? moment(pc_tracker.modified_time) : null;
        });
        return res;
    }
}
