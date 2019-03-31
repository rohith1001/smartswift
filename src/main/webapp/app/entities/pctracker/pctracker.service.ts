import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPctracker } from 'app/shared/model/pctracker.model';

type EntityResponseType = HttpResponse<IPctracker>;
type EntityArrayResponseType = HttpResponse<IPctracker[]>;

@Injectable({ providedIn: 'root' })
export class PctrackerService {
    private resourceUrl = SERVER_API_URL + 'api/pctrackers';
    public spctrackers: IPctracker[];
    public searchText = '';

    constructor(private http: HttpClient) {}

    create(pctracker: IPctracker): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pctracker);
        return this.http
            .post<IPctracker>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(pctracker: IPctracker): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pctracker);
        return this.http
            .put<IPctracker>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPctracker>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPctracker[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(pctracker: IPctracker): IPctracker {
        const copy: IPctracker = Object.assign({}, pctracker, {
            date_received: pctracker.date_received != null && pctracker.date_received.isValid() ? pctracker.date_received.toJSON() : null,
            iia_deliver_date_planned:
                pctracker.iia_deliver_date_planned != null && pctracker.iia_deliver_date_planned.isValid()
                    ? pctracker.iia_deliver_date_planned.toJSON()
                    : null,
            iia_deliver_date_actual:
                pctracker.iia_deliver_date_actual != null && pctracker.iia_deliver_date_actual.isValid()
                    ? pctracker.iia_deliver_date_actual.toJSON()
                    : null,
            dcd_deliver_date_planned:
                pctracker.dcd_deliver_date_planned != null && pctracker.dcd_deliver_date_planned.isValid()
                    ? pctracker.dcd_deliver_date_planned.toJSON()
                    : null,
            dcd_deliver_date_actual:
                pctracker.dcd_deliver_date_actual != null && pctracker.dcd_deliver_date_actual.isValid()
                    ? pctracker.dcd_deliver_date_actual.toJSON()
                    : null,
            wr_acknowledge_date_planned:
                pctracker.wr_acknowledge_date_planned != null && pctracker.wr_acknowledge_date_planned.isValid()
                    ? pctracker.wr_acknowledge_date_planned.toJSON()
                    : null,
            wr_acknowledge_date_actual:
                pctracker.wr_acknowledge_date_actual != null && pctracker.wr_acknowledge_date_actual.isValid()
                    ? pctracker.wr_acknowledge_date_actual.toJSON()
                    : null,
            wr_costready_date_planned:
                pctracker.wr_costready_date_planned != null && pctracker.wr_costready_date_planned.isValid()
                    ? pctracker.wr_costready_date_planned.toJSON()
                    : null,
            wr_costready_date_actual:
                pctracker.wr_costready_date_actual != null && pctracker.wr_costready_date_actual.isValid()
                    ? pctracker.wr_costready_date_actual.toJSON()
                    : null,
            hlcd_delivery_date_planned:
                pctracker.hlcd_delivery_date_planned != null && pctracker.hlcd_delivery_date_planned.isValid()
                    ? pctracker.hlcd_delivery_date_planned.toJSON()
                    : null,
            hlcd_delivery_date_actual:
                pctracker.hlcd_delivery_date_actual != null && pctracker.hlcd_delivery_date_actual.isValid()
                    ? pctracker.hlcd_delivery_date_actual.toJSON()
                    : null,
            test_ready_date_planned:
                pctracker.test_ready_date_planned != null && pctracker.test_ready_date_planned.isValid()
                    ? pctracker.test_ready_date_planned.toJSON()
                    : null,
            test_ready_date_actual:
                pctracker.test_ready_date_actual != null && pctracker.test_ready_date_actual.isValid()
                    ? pctracker.test_ready_date_actual.toJSON()
                    : null,
            launch_date_planned:
                pctracker.launch_date_planned != null && pctracker.launch_date_planned.isValid()
                    ? pctracker.launch_date_planned.toJSON()
                    : null,
            launch_date_actual:
                pctracker.launch_date_actual != null && pctracker.launch_date_actual.isValid()
                    ? pctracker.launch_date_actual.toJSON()
                    : null,
            delivery_date_planned:
                pctracker.delivery_date_planned != null && pctracker.delivery_date_planned.isValid()
                    ? pctracker.delivery_date_planned.toJSON()
                    : null,
            delivery_date_actual:
                pctracker.delivery_date_actual != null && pctracker.delivery_date_actual.isValid()
                    ? pctracker.delivery_date_actual.toJSON()
                    : null,
            modified_time: pctracker.modified_time != null && pctracker.modified_time.isValid() ? pctracker.modified_time.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date_received = res.body.date_received != null ? moment(res.body.date_received) : null;
        res.body.iia_deliver_date_planned = res.body.iia_deliver_date_planned != null ? moment(res.body.iia_deliver_date_planned) : null;
        res.body.iia_deliver_date_actual = res.body.iia_deliver_date_actual != null ? moment(res.body.iia_deliver_date_actual) : null;
        res.body.dcd_deliver_date_planned = res.body.dcd_deliver_date_planned != null ? moment(res.body.dcd_deliver_date_planned) : null;
        res.body.dcd_deliver_date_actual = res.body.dcd_deliver_date_actual != null ? moment(res.body.dcd_deliver_date_actual) : null;
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
        res.body.forEach((pctracker: IPctracker) => {
            pctracker.date_received = pctracker.date_received != null ? moment(pctracker.date_received) : null;
            pctracker.iia_deliver_date_planned =
                pctracker.iia_deliver_date_planned != null ? moment(pctracker.iia_deliver_date_planned) : null;
            pctracker.iia_deliver_date_actual =
                pctracker.iia_deliver_date_actual != null ? moment(pctracker.iia_deliver_date_actual) : null;
            pctracker.dcd_deliver_date_planned =
                pctracker.dcd_deliver_date_planned != null ? moment(pctracker.dcd_deliver_date_planned) : null;
            pctracker.dcd_deliver_date_actual =
                pctracker.dcd_deliver_date_actual != null ? moment(pctracker.dcd_deliver_date_actual) : null;
            pctracker.wr_acknowledge_date_planned =
                pctracker.wr_acknowledge_date_planned != null ? moment(pctracker.wr_acknowledge_date_planned) : null;
            pctracker.wr_acknowledge_date_actual =
                pctracker.wr_acknowledge_date_actual != null ? moment(pctracker.wr_acknowledge_date_actual) : null;
            pctracker.wr_costready_date_planned =
                pctracker.wr_costready_date_planned != null ? moment(pctracker.wr_costready_date_planned) : null;
            pctracker.wr_costready_date_actual =
                pctracker.wr_costready_date_actual != null ? moment(pctracker.wr_costready_date_actual) : null;
            pctracker.hlcd_delivery_date_planned =
                pctracker.hlcd_delivery_date_planned != null ? moment(pctracker.hlcd_delivery_date_planned) : null;
            pctracker.hlcd_delivery_date_actual =
                pctracker.hlcd_delivery_date_actual != null ? moment(pctracker.hlcd_delivery_date_actual) : null;
            pctracker.test_ready_date_planned =
                pctracker.test_ready_date_planned != null ? moment(pctracker.test_ready_date_planned) : null;
            pctracker.test_ready_date_actual = pctracker.test_ready_date_actual != null ? moment(pctracker.test_ready_date_actual) : null;
            pctracker.launch_date_planned = pctracker.launch_date_planned != null ? moment(pctracker.launch_date_planned) : null;
            pctracker.launch_date_actual = pctracker.launch_date_actual != null ? moment(pctracker.launch_date_actual) : null;
            pctracker.delivery_date_planned = pctracker.delivery_date_planned != null ? moment(pctracker.delivery_date_planned) : null;
            pctracker.delivery_date_actual = pctracker.delivery_date_actual != null ? moment(pctracker.delivery_date_actual) : null;
            pctracker.modified_time = pctracker.modified_time != null ? moment(pctracker.modified_time) : null;
        });
        return res;
    }
}
