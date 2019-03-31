import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IL2query } from 'app/shared/model/l-2-query.model';

type EntityResponseType = HttpResponse<IL2query>;
type EntityArrayResponseType = HttpResponse<IL2query[]>;

@Injectable({ providedIn: 'root' })
export class L2queryService {
    private resourceUrl = SERVER_API_URL + 'api/l-2-queries';

    constructor(private http: HttpClient) {}

    create(l2query: IL2query): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(l2query);
        return this.http
            .post<IL2query>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(l2query: IL2query): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(l2query);
        return this.http
            .put<IL2query>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IL2query>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IL2query[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(l2query: IL2query): IL2query {
        const copy: IL2query = Object.assign({}, l2query, {
            stk_start_date: l2query.stk_start_date != null && l2query.stk_start_date.isValid() ? l2query.stk_start_date.toJSON() : null,
            requested_date: l2query.requested_date != null && l2query.requested_date.isValid() ? l2query.requested_date.toJSON() : null,
            responded_date: l2query.responded_date != null && l2query.responded_date.isValid() ? l2query.responded_date.toJSON() : null,
            modified_time: l2query.modified_time != null && l2query.modified_time.isValid() ? l2query.modified_time.toJSON() : null,
            rca_completed_date:
                l2query.rca_completed_date != null && l2query.rca_completed_date.isValid() ? l2query.rca_completed_date.toJSON() : null,
            nttdata_start_date:
                l2query.nttdata_start_date != null && l2query.nttdata_start_date.isValid() ? l2query.nttdata_start_date.toJSON() : null,
            solution_found_date:
                l2query.solution_found_date != null && l2query.solution_found_date.isValid() ? l2query.solution_found_date.toJSON() : null,
            passed_back_date:
                l2query.passed_back_date != null && l2query.passed_back_date.isValid() ? l2query.passed_back_date.toJSON() : null,
            re_assigned_date:
                l2query.re_assigned_date != null && l2query.re_assigned_date.isValid() ? l2query.re_assigned_date.toJSON() : null,
            passed_back_date1:
                l2query.passed_back_date1 != null && l2query.passed_back_date1.isValid() ? l2query.passed_back_date1.toJSON() : null,
            re_assigned_date1:
                l2query.re_assigned_date1 != null && l2query.re_assigned_date1.isValid() ? l2query.re_assigned_date1.toJSON() : null,
            passed_back_date2:
                l2query.passed_back_date2 != null && l2query.passed_back_date2.isValid() ? l2query.passed_back_date2.toJSON() : null,
            re_assigned_date2:
                l2query.re_assigned_date2 != null && l2query.re_assigned_date2.isValid() ? l2query.re_assigned_date2.toJSON() : null,
            passed_back_date3:
                l2query.passed_back_date3 != null && l2query.passed_back_date3.isValid() ? l2query.passed_back_date3.toJSON() : null,
            re_assigned_date3:
                l2query.re_assigned_date3 != null && l2query.re_assigned_date3.isValid() ? l2query.re_assigned_date3.toJSON() : null,
            passed_back_date4:
                l2query.passed_back_date4 != null && l2query.passed_back_date4.isValid() ? l2query.passed_back_date4.toJSON() : null,
            re_assigned_date4:
                l2query.re_assigned_date4 != null && l2query.re_assigned_date4.isValid() ? l2query.re_assigned_date4.toJSON() : null,
            passed_back_date5:
                l2query.passed_back_date5 != null && l2query.passed_back_date5.isValid() ? l2query.passed_back_date5.toJSON() : null,
            re_assigned_date5:
                l2query.re_assigned_date5 != null && l2query.re_assigned_date5.isValid() ? l2query.re_assigned_date5.toJSON() : null,
            closed_date: l2query.closed_date != null && l2query.closed_date.isValid() ? l2query.closed_date.toJSON() : null,
            ter_date: l2query.ter_date != null && l2query.ter_date.isValid() ? l2query.ter_date.toJSON() : null,
            rrt_start_date: l2query.rrt_start_date != null && l2query.rrt_start_date.isValid() ? l2query.rrt_start_date.toJSON() : null,
            rrt_completion_date:
                l2query.rrt_completion_date != null && l2query.rrt_completion_date.isValid() ? l2query.rrt_completion_date.toJSON() : null,
            live_date: l2query.live_date != null && l2query.live_date.isValid() ? l2query.live_date.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.stk_start_date = res.body.stk_start_date != null ? moment(res.body.stk_start_date) : null;
        res.body.requested_date = res.body.requested_date != null ? moment(res.body.requested_date) : null;
        res.body.responded_date = res.body.responded_date != null ? moment(res.body.responded_date) : null;
        res.body.modified_time = res.body.modified_time != null ? moment(res.body.modified_time) : null;
        res.body.rca_completed_date = res.body.rca_completed_date != null ? moment(res.body.rca_completed_date) : null;
        res.body.nttdata_start_date = res.body.nttdata_start_date != null ? moment(res.body.nttdata_start_date) : null;
        res.body.solution_found_date = res.body.solution_found_date != null ? moment(res.body.solution_found_date) : null;
        res.body.passed_back_date = res.body.passed_back_date != null ? moment(res.body.passed_back_date) : null;
        res.body.re_assigned_date = res.body.re_assigned_date != null ? moment(res.body.re_assigned_date) : null;
        res.body.passed_back_date1 = res.body.passed_back_date1 != null ? moment(res.body.passed_back_date1) : null;
        res.body.re_assigned_date1 = res.body.re_assigned_date1 != null ? moment(res.body.re_assigned_date1) : null;
        res.body.passed_back_date2 = res.body.passed_back_date2 != null ? moment(res.body.passed_back_date2) : null;
        res.body.re_assigned_date2 = res.body.re_assigned_date2 != null ? moment(res.body.re_assigned_date2) : null;
        res.body.passed_back_date3 = res.body.passed_back_date3 != null ? moment(res.body.passed_back_date3) : null;
        res.body.re_assigned_date3 = res.body.re_assigned_date3 != null ? moment(res.body.re_assigned_date3) : null;
        res.body.passed_back_date4 = res.body.passed_back_date4 != null ? moment(res.body.passed_back_date4) : null;
        res.body.re_assigned_date4 = res.body.re_assigned_date4 != null ? moment(res.body.re_assigned_date4) : null;
        res.body.passed_back_date5 = res.body.passed_back_date5 != null ? moment(res.body.passed_back_date5) : null;
        res.body.re_assigned_date5 = res.body.re_assigned_date5 != null ? moment(res.body.re_assigned_date5) : null;
        res.body.closed_date = res.body.closed_date != null ? moment(res.body.closed_date) : null;
        res.body.ter_date = res.body.ter_date != null ? moment(res.body.ter_date) : null;
        res.body.rrt_start_date = res.body.rrt_start_date != null ? moment(res.body.rrt_start_date) : null;
        res.body.rrt_completion_date = res.body.rrt_completion_date != null ? moment(res.body.rrt_completion_date) : null;
        res.body.live_date = res.body.live_date != null ? moment(res.body.live_date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((l2query: IL2query) => {
            l2query.stk_start_date = l2query.stk_start_date != null ? moment(l2query.stk_start_date) : null;
            l2query.requested_date = l2query.requested_date != null ? moment(l2query.requested_date) : null;
            l2query.responded_date = l2query.responded_date != null ? moment(l2query.responded_date) : null;
            l2query.modified_time = l2query.modified_time != null ? moment(l2query.modified_time) : null;
            l2query.rca_completed_date = l2query.rca_completed_date != null ? moment(l2query.rca_completed_date) : null;
            l2query.nttdata_start_date = l2query.nttdata_start_date != null ? moment(l2query.nttdata_start_date) : null;
            l2query.solution_found_date = l2query.solution_found_date != null ? moment(l2query.solution_found_date) : null;
            l2query.passed_back_date = l2query.passed_back_date != null ? moment(l2query.passed_back_date) : null;
            l2query.re_assigned_date = l2query.re_assigned_date != null ? moment(l2query.re_assigned_date) : null;
            l2query.passed_back_date1 = l2query.passed_back_date1 != null ? moment(l2query.passed_back_date1) : null;
            l2query.re_assigned_date1 = l2query.re_assigned_date1 != null ? moment(l2query.re_assigned_date1) : null;
            l2query.passed_back_date2 = l2query.passed_back_date2 != null ? moment(l2query.passed_back_date2) : null;
            l2query.re_assigned_date2 = l2query.re_assigned_date2 != null ? moment(l2query.re_assigned_date2) : null;
            l2query.passed_back_date3 = l2query.passed_back_date3 != null ? moment(l2query.passed_back_date3) : null;
            l2query.re_assigned_date3 = l2query.re_assigned_date3 != null ? moment(l2query.re_assigned_date3) : null;
            l2query.passed_back_date4 = l2query.passed_back_date4 != null ? moment(l2query.passed_back_date4) : null;
            l2query.re_assigned_date4 = l2query.re_assigned_date4 != null ? moment(l2query.re_assigned_date4) : null;
            l2query.passed_back_date5 = l2query.passed_back_date5 != null ? moment(l2query.passed_back_date5) : null;
            l2query.re_assigned_date5 = l2query.re_assigned_date5 != null ? moment(l2query.re_assigned_date5) : null;
            l2query.closed_date = l2query.closed_date != null ? moment(l2query.closed_date) : null;
            l2query.ter_date = l2query.ter_date != null ? moment(l2query.ter_date) : null;
            l2query.rrt_start_date = l2query.rrt_start_date != null ? moment(l2query.rrt_start_date) : null;
            l2query.rrt_completion_date = l2query.rrt_completion_date != null ? moment(l2query.rrt_completion_date) : null;
            l2query.live_date = l2query.live_date != null ? moment(l2query.live_date) : null;
        });
        return res;
    }
}
