/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { L2queryService } from 'app/entities/l-2-query/l-2-query.service';
import { IL2query, L2query } from 'app/shared/model/l-2-query.model';

describe('Service Tests', () => {
    describe('L2query Service', () => {
        let injector: TestBed;
        let service: L2queryService;
        let httpMock: HttpTestingController;
        let elemDefault: IL2query;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(L2queryService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new L2query(
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                currentDate,
                'AAAAAAA',
                currentDate,
                0,
                currentDate,
                currentDate,
                0,
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                'AAAAAAA',
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                0,
                false
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        stk_start_date: currentDate.format(DATE_TIME_FORMAT),
                        requested_date: currentDate.format(DATE_TIME_FORMAT),
                        responded_date: currentDate.format(DATE_TIME_FORMAT),
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        rca_completed_date: currentDate.format(DATE_TIME_FORMAT),
                        nttdata_start_date: currentDate.format(DATE_TIME_FORMAT),
                        solution_found_date: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date1: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date1: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date2: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date2: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date3: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date3: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date4: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date4: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date5: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date5: currentDate.format(DATE_TIME_FORMAT),
                        closed_date: currentDate.format(DATE_TIME_FORMAT),
                        ter_date: currentDate.format(DATE_TIME_FORMAT),
                        rrt_start_date: currentDate.format(DATE_TIME_FORMAT),
                        rrt_completion_date: currentDate.format(DATE_TIME_FORMAT),
                        live_date: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a L2query', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        stk_start_date: currentDate.format(DATE_TIME_FORMAT),
                        requested_date: currentDate.format(DATE_TIME_FORMAT),
                        responded_date: currentDate.format(DATE_TIME_FORMAT),
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        rca_completed_date: currentDate.format(DATE_TIME_FORMAT),
                        nttdata_start_date: currentDate.format(DATE_TIME_FORMAT),
                        solution_found_date: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date1: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date1: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date2: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date2: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date3: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date3: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date4: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date4: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date5: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date5: currentDate.format(DATE_TIME_FORMAT),
                        closed_date: currentDate.format(DATE_TIME_FORMAT),
                        ter_date: currentDate.format(DATE_TIME_FORMAT),
                        rrt_start_date: currentDate.format(DATE_TIME_FORMAT),
                        rrt_completion_date: currentDate.format(DATE_TIME_FORMAT),
                        live_date: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        stk_start_date: currentDate,
                        requested_date: currentDate,
                        responded_date: currentDate,
                        modified_time: currentDate,
                        rca_completed_date: currentDate,
                        nttdata_start_date: currentDate,
                        solution_found_date: currentDate,
                        passed_back_date: currentDate,
                        re_assigned_date: currentDate,
                        passed_back_date1: currentDate,
                        re_assigned_date1: currentDate,
                        passed_back_date2: currentDate,
                        re_assigned_date2: currentDate,
                        passed_back_date3: currentDate,
                        re_assigned_date3: currentDate,
                        passed_back_date4: currentDate,
                        re_assigned_date4: currentDate,
                        passed_back_date5: currentDate,
                        re_assigned_date5: currentDate,
                        closed_date: currentDate,
                        ter_date: currentDate,
                        rrt_start_date: currentDate,
                        rrt_completion_date: currentDate,
                        live_date: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new L2query(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a L2query', async () => {
                const returnedFromService = Object.assign(
                    {
                        stk_number: 'BBBBBB',
                        stk_description: 'BBBBBB',
                        stk_start_date: currentDate.format(DATE_TIME_FORMAT),
                        requested_date: currentDate.format(DATE_TIME_FORMAT),
                        responded_date: currentDate.format(DATE_TIME_FORMAT),
                        stk_comment: 'BBBBBB',
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        rca_completed: 1,
                        rca_completed_date: currentDate.format(DATE_TIME_FORMAT),
                        nttdata_start_date: currentDate.format(DATE_TIME_FORMAT),
                        solution_found: 1,
                        solution_found_date: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date1: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date1: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date2: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date2: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date3: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date3: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date4: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date4: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date5: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date5: currentDate.format(DATE_TIME_FORMAT),
                        closed_date: currentDate.format(DATE_TIME_FORMAT),
                        root_cause_description: 'BBBBBB',
                        ter_date: currentDate.format(DATE_TIME_FORMAT),
                        rrt_start_date: currentDate.format(DATE_TIME_FORMAT),
                        rrt_completion_date: currentDate.format(DATE_TIME_FORMAT),
                        live_date: currentDate.format(DATE_TIME_FORMAT),
                        rca_fix_success: 1,
                        ops_sla_breached: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        stk_start_date: currentDate,
                        requested_date: currentDate,
                        responded_date: currentDate,
                        modified_time: currentDate,
                        rca_completed_date: currentDate,
                        nttdata_start_date: currentDate,
                        solution_found_date: currentDate,
                        passed_back_date: currentDate,
                        re_assigned_date: currentDate,
                        passed_back_date1: currentDate,
                        re_assigned_date1: currentDate,
                        passed_back_date2: currentDate,
                        re_assigned_date2: currentDate,
                        passed_back_date3: currentDate,
                        re_assigned_date3: currentDate,
                        passed_back_date4: currentDate,
                        re_assigned_date4: currentDate,
                        passed_back_date5: currentDate,
                        re_assigned_date5: currentDate,
                        closed_date: currentDate,
                        ter_date: currentDate,
                        rrt_start_date: currentDate,
                        rrt_completion_date: currentDate,
                        live_date: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of L2query', async () => {
                const returnedFromService = Object.assign(
                    {
                        stk_number: 'BBBBBB',
                        stk_description: 'BBBBBB',
                        stk_start_date: currentDate.format(DATE_TIME_FORMAT),
                        requested_date: currentDate.format(DATE_TIME_FORMAT),
                        responded_date: currentDate.format(DATE_TIME_FORMAT),
                        stk_comment: 'BBBBBB',
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        rca_completed: 1,
                        rca_completed_date: currentDate.format(DATE_TIME_FORMAT),
                        nttdata_start_date: currentDate.format(DATE_TIME_FORMAT),
                        solution_found: 1,
                        solution_found_date: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date1: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date1: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date2: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date2: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date3: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date3: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date4: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date4: currentDate.format(DATE_TIME_FORMAT),
                        passed_back_date5: currentDate.format(DATE_TIME_FORMAT),
                        re_assigned_date5: currentDate.format(DATE_TIME_FORMAT),
                        closed_date: currentDate.format(DATE_TIME_FORMAT),
                        root_cause_description: 'BBBBBB',
                        ter_date: currentDate.format(DATE_TIME_FORMAT),
                        rrt_start_date: currentDate.format(DATE_TIME_FORMAT),
                        rrt_completion_date: currentDate.format(DATE_TIME_FORMAT),
                        live_date: currentDate.format(DATE_TIME_FORMAT),
                        rca_fix_success: 1,
                        ops_sla_breached: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        stk_start_date: currentDate,
                        requested_date: currentDate,
                        responded_date: currentDate,
                        modified_time: currentDate,
                        rca_completed_date: currentDate,
                        nttdata_start_date: currentDate,
                        solution_found_date: currentDate,
                        passed_back_date: currentDate,
                        re_assigned_date: currentDate,
                        passed_back_date1: currentDate,
                        re_assigned_date1: currentDate,
                        passed_back_date2: currentDate,
                        re_assigned_date2: currentDate,
                        passed_back_date3: currentDate,
                        re_assigned_date3: currentDate,
                        passed_back_date4: currentDate,
                        re_assigned_date4: currentDate,
                        passed_back_date5: currentDate,
                        re_assigned_date5: currentDate,
                        closed_date: currentDate,
                        ter_date: currentDate,
                        rrt_start_date: currentDate,
                        rrt_completion_date: currentDate,
                        live_date: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a L2query', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
