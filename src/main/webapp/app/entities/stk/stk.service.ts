import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStk } from 'app/shared/model/stk.model';

type EntityResponseType = HttpResponse<IStk>;
type EntityArrayResponseType = HttpResponse<IStk[]>;

@Injectable({ providedIn: 'root' })
export class StkService {
    private resourceUrl = SERVER_API_URL + 'api/stks';

    constructor(private http: HttpClient) {}

    create(stk: IStk): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stk);
        return this.http
            .post<IStk>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(stk: IStk): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stk);
        return this.http
            .put<IStk>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStk>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStk[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(stk: IStk): IStk {
        const copy: IStk = Object.assign({}, stk, {
            stk_start_date: stk.stk_start_date != null && stk.stk_start_date.isValid() ? stk.stk_start_date.toJSON() : null,
            requested_date: stk.requested_date != null && stk.requested_date.isValid() ? stk.requested_date.toJSON() : null,
            responded_time: stk.responded_time != null && stk.responded_time.isValid() ? stk.responded_time.toJSON() : null,
            modified_time: stk.modified_time != null && stk.modified_time.isValid() ? stk.modified_time.toJSON() : null,
            nttdata_start_date: stk.nttdata_start_date != null && stk.nttdata_start_date.isValid() ? stk.nttdata_start_date.toJSON() : null,
            rca_completed_date: stk.rca_completed_date != null && stk.rca_completed_date.isValid() ? stk.rca_completed_date.toJSON() : null,
            solution_found_date:
                stk.solution_found_date != null && stk.solution_found_date.isValid() ? stk.solution_found_date.toJSON() : null,
            passed_back_date: stk.passed_back_date != null && stk.passed_back_date.isValid() ? stk.passed_back_date.toJSON() : null,
            re_assigned_date: stk.re_assigned_date != null && stk.re_assigned_date.isValid() ? stk.re_assigned_date.toJSON() : null,
            passed_back_date1: stk.passed_back_date1 != null && stk.passed_back_date1.isValid() ? stk.passed_back_date1.toJSON() : null,
            re_assigned_date1: stk.re_assigned_date1 != null && stk.re_assigned_date1.isValid() ? stk.re_assigned_date1.toJSON() : null,
            passed_back_date2: stk.passed_back_date2 != null && stk.passed_back_date2.isValid() ? stk.passed_back_date2.toJSON() : null,
            re_assigned_date2: stk.re_assigned_date2 != null && stk.re_assigned_date2.isValid() ? stk.re_assigned_date2.toJSON() : null,
            passed_back_date3: stk.passed_back_date3 != null && stk.passed_back_date3.isValid() ? stk.passed_back_date3.toJSON() : null,
            re_assigned_date3: stk.re_assigned_date3 != null && stk.re_assigned_date3.isValid() ? stk.re_assigned_date3.toJSON() : null,
            passed_back_date4: stk.passed_back_date4 != null && stk.passed_back_date4.isValid() ? stk.passed_back_date4.toJSON() : null,
            re_assigned_date4: stk.re_assigned_date4 != null && stk.re_assigned_date4.isValid() ? stk.re_assigned_date4.toJSON() : null,
            passed_back_date5: stk.passed_back_date5 != null && stk.passed_back_date5.isValid() ? stk.passed_back_date5.toJSON() : null,
            re_assigned_date5: stk.re_assigned_date5 != null && stk.re_assigned_date5.isValid() ? stk.re_assigned_date5.toJSON() : null,
            closed_date: stk.closed_date != null && stk.closed_date.isValid() ? stk.closed_date.toJSON() : null,
            ter_date: stk.ter_date != null && stk.ter_date.isValid() ? stk.ter_date.toJSON() : null,
            rrt_start_date: stk.rrt_start_date != null && stk.rrt_start_date.isValid() ? stk.rrt_start_date.toJSON() : null,
            rrt_completion_date:
                stk.rrt_completion_date != null && stk.rrt_completion_date.isValid() ? stk.rrt_completion_date.toJSON() : null,
            live_date: stk.live_date != null && stk.live_date.isValid() ? stk.live_date.toJSON() : null,
            finaldate_rca: stk.finaldate_rca != null && stk.finaldate_rca.isValid() ? stk.finaldate_rca.toJSON() : null,
            finaldate_solution: stk.finaldate_solution != null && stk.finaldate_solution.isValid() ? stk.finaldate_solution.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.stk_start_date = res.body.stk_start_date != null ? moment(res.body.stk_start_date) : null;
        res.body.requested_date = res.body.requested_date != null ? moment(res.body.requested_date) : null;
        res.body.responded_time = res.body.responded_time != null ? moment(res.body.responded_time) : null;
        res.body.modified_time = res.body.modified_time != null ? moment(res.body.modified_time) : null;
        res.body.nttdata_start_date = res.body.nttdata_start_date != null ? moment(res.body.nttdata_start_date) : null;
        res.body.rca_completed_date = res.body.rca_completed_date != null ? moment(res.body.rca_completed_date) : null;
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
        res.body.finaldate_rca = res.body.finaldate_rca != null ? moment(res.body.finaldate_rca) : null;
        res.body.finaldate_solution = res.body.finaldate_solution != null ? moment(res.body.finaldate_solution) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((stk: IStk) => {
            stk.stk_start_date = stk.stk_start_date != null ? moment(stk.stk_start_date) : null;
            stk.requested_date = stk.requested_date != null ? moment(stk.requested_date) : null;
            stk.responded_time = stk.responded_time != null ? moment(stk.responded_time) : null;
            stk.modified_time = stk.modified_time != null ? moment(stk.modified_time) : null;
            stk.nttdata_start_date = stk.nttdata_start_date != null ? moment(stk.nttdata_start_date) : null;
            stk.rca_completed_date = stk.rca_completed_date != null ? moment(stk.rca_completed_date) : null;
            stk.solution_found_date = stk.solution_found_date != null ? moment(stk.solution_found_date) : null;
            stk.passed_back_date = stk.passed_back_date != null ? moment(stk.passed_back_date) : null;
            stk.re_assigned_date = stk.re_assigned_date != null ? moment(stk.re_assigned_date) : null;
            stk.passed_back_date1 = stk.passed_back_date1 != null ? moment(stk.passed_back_date1) : null;
            stk.re_assigned_date1 = stk.re_assigned_date1 != null ? moment(stk.re_assigned_date1) : null;
            stk.passed_back_date2 = stk.passed_back_date2 != null ? moment(stk.passed_back_date2) : null;
            stk.re_assigned_date2 = stk.re_assigned_date2 != null ? moment(stk.re_assigned_date2) : null;
            stk.passed_back_date3 = stk.passed_back_date3 != null ? moment(stk.passed_back_date3) : null;
            stk.re_assigned_date3 = stk.re_assigned_date3 != null ? moment(stk.re_assigned_date3) : null;
            stk.passed_back_date4 = stk.passed_back_date4 != null ? moment(stk.passed_back_date4) : null;
            stk.re_assigned_date4 = stk.re_assigned_date4 != null ? moment(stk.re_assigned_date4) : null;
            stk.passed_back_date5 = stk.passed_back_date5 != null ? moment(stk.passed_back_date5) : null;
            stk.re_assigned_date5 = stk.re_assigned_date5 != null ? moment(stk.re_assigned_date5) : null;
            stk.closed_date = stk.closed_date != null ? moment(stk.closed_date) : null;
            stk.ter_date = stk.ter_date != null ? moment(stk.ter_date) : null;
            stk.rrt_start_date = stk.rrt_start_date != null ? moment(stk.rrt_start_date) : null;
            stk.rrt_completion_date = stk.rrt_completion_date != null ? moment(stk.rrt_completion_date) : null;
            stk.live_date = stk.live_date != null ? moment(stk.live_date) : null;
            stk.finaldate_rca = stk.finaldate_rca != null ? moment(stk.finaldate_rca) : null;
            stk.finaldate_solution = stk.finaldate_solution != null ? moment(stk.finaldate_solution) : null;
        });
        return res;
    }
}
