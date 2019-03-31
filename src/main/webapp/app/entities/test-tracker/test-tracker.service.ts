import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITest_tracker } from 'app/shared/model/test-tracker.model';

type EntityResponseType = HttpResponse<ITest_tracker>;
type EntityArrayResponseType = HttpResponse<ITest_tracker[]>;

@Injectable({ providedIn: 'root' })
export class Test_trackerService {
    private resourceUrl = SERVER_API_URL + 'api/test-trackers';

    constructor(private http: HttpClient) {}

    create(test_tracker: ITest_tracker): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(test_tracker);
        return this.http
            .post<ITest_tracker>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(test_tracker: ITest_tracker): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(test_tracker);
        return this.http
            .put<ITest_tracker>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITest_tracker>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITest_tracker[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(test_tracker: ITest_tracker): ITest_tracker {
        const copy: ITest_tracker = Object.assign({}, test_tracker, {
            detected_on_date:
                test_tracker.detected_on_date != null && test_tracker.detected_on_date.isValid()
                    ? test_tracker.detected_on_date.format(DATE_FORMAT)
                    : null,
            first_fix_date:
                test_tracker.first_fix_date != null && test_tracker.first_fix_date.isValid() ? test_tracker.first_fix_date.toJSON() : null,
            last_fix_date:
                test_tracker.last_fix_date != null && test_tracker.last_fix_date.isValid() ? test_tracker.last_fix_date.toJSON() : null,
            fix_due_date:
                test_tracker.fix_due_date != null && test_tracker.fix_due_date.isValid() ? test_tracker.fix_due_date.toJSON() : null,
            closing_date:
                test_tracker.closing_date != null && test_tracker.closing_date.isValid() ? test_tracker.closing_date.toJSON() : null,
            modified_time:
                test_tracker.modified_time != null && test_tracker.modified_time.isValid() ? test_tracker.modified_time.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.detected_on_date = res.body.detected_on_date != null ? moment(res.body.detected_on_date) : null;
        res.body.first_fix_date = res.body.first_fix_date != null ? moment(res.body.first_fix_date) : null;
        res.body.last_fix_date = res.body.last_fix_date != null ? moment(res.body.last_fix_date) : null;
        res.body.fix_due_date = res.body.fix_due_date != null ? moment(res.body.fix_due_date) : null;
        res.body.closing_date = res.body.closing_date != null ? moment(res.body.closing_date) : null;
        res.body.modified_time = res.body.modified_time != null ? moment(res.body.modified_time) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((test_tracker: ITest_tracker) => {
            test_tracker.detected_on_date = test_tracker.detected_on_date != null ? moment(test_tracker.detected_on_date) : null;
            test_tracker.first_fix_date = test_tracker.first_fix_date != null ? moment(test_tracker.first_fix_date) : null;
            test_tracker.last_fix_date = test_tracker.last_fix_date != null ? moment(test_tracker.last_fix_date) : null;
            test_tracker.fix_due_date = test_tracker.fix_due_date != null ? moment(test_tracker.fix_due_date) : null;
            test_tracker.closing_date = test_tracker.closing_date != null ? moment(test_tracker.closing_date) : null;
            test_tracker.modified_time = test_tracker.modified_time != null ? moment(test_tracker.modified_time) : null;
        });
        return res;
    }
}
