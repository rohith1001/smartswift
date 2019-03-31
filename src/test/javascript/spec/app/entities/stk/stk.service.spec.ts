/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { StkService } from 'app/entities/stk/stk.service';
import { IStk, Stk } from 'app/shared/model/stk.model';

describe('Service Tests', () => {
    describe('Stk Service', () => {
        let injector: TestBed;
        let service: StkService;
        let httpMock: HttpTestingController;
        let elemDefault: IStk;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(StkService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Stk(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
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
                false,
                'AAAAAAA',
                false,
                'AAAAAAA',
                false,
                'AAAAAAA',
                false,
                'AAAAAAA',
                false,
                'AAAAAAA',
                false,
                'AAAAAAA',
                false,
                false,
                false,
                currentDate,
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        stk_start_date: currentDate.format(DATE_TIME_FORMAT),
                        requested_date: currentDate.format(DATE_TIME_FORMAT),
                        responded_time: currentDate.format(DATE_TIME_FORMAT),
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        nttdata_start_date: currentDate.format(DATE_TIME_FORMAT),
                        rca_completed_date: currentDate.format(DATE_TIME_FORMAT),
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
                        live_date: currentDate.format(DATE_TIME_FORMAT),
                        finaldate_rca: currentDate.format(DATE_TIME_FORMAT),
                        finaldate_solution: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a Stk', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        stk_start_date: currentDate.format(DATE_TIME_FORMAT),
                        requested_date: currentDate.format(DATE_TIME_FORMAT),
                        responded_time: currentDate.format(DATE_TIME_FORMAT),
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        nttdata_start_date: currentDate.format(DATE_TIME_FORMAT),
                        rca_completed_date: currentDate.format(DATE_TIME_FORMAT),
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
                        live_date: currentDate.format(DATE_TIME_FORMAT),
                        finaldate_rca: currentDate.format(DATE_TIME_FORMAT),
                        finaldate_solution: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        stk_start_date: currentDate,
                        requested_date: currentDate,
                        responded_time: currentDate,
                        modified_time: currentDate,
                        nttdata_start_date: currentDate,
                        rca_completed_date: currentDate,
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
                        live_date: currentDate,
                        finaldate_rca: currentDate,
                        finaldate_solution: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Stk(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Stk', async () => {
                const returnedFromService = Object.assign(
                    {
                        stk_number: 'BBBBBB',
                        stk_description: 'BBBBBB',
                        stk_comment: 'BBBBBB',
                        root_cause_description: 'BBBBBB',
                        rca_fix_success: 'BBBBBB',
                        stk_start_date: currentDate.format(DATE_TIME_FORMAT),
                        requested_date: currentDate.format(DATE_TIME_FORMAT),
                        responded_time: currentDate.format(DATE_TIME_FORMAT),
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        nttdata_start_date: currentDate.format(DATE_TIME_FORMAT),
                        rca_completed_date: currentDate.format(DATE_TIME_FORMAT),
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
                        live_date: currentDate.format(DATE_TIME_FORMAT),
                        km1: 'BBBBBB',
                        km1_breached: true,
                        km2: 'BBBBBB',
                        km2_breached: true,
                        km3: 'BBBBBB',
                        km3_breached: true,
                        qm1: 'BBBBBB',
                        qm1_breached: true,
                        qm2: 'BBBBBB',
                        qm2_breached: true,
                        qm3: 'BBBBBB',
                        qm3_breached: true,
                        qm4: 'BBBBBB',
                        qm4_breached: true,
                        rca_completed: true,
                        solution_found: true,
                        finaldate_rca: currentDate.format(DATE_TIME_FORMAT),
                        finaldate_solution: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        stk_start_date: currentDate,
                        requested_date: currentDate,
                        responded_time: currentDate,
                        modified_time: currentDate,
                        nttdata_start_date: currentDate,
                        rca_completed_date: currentDate,
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
                        live_date: currentDate,
                        finaldate_rca: currentDate,
                        finaldate_solution: currentDate
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

            it('should return a list of Stk', async () => {
                const returnedFromService = Object.assign(
                    {
                        stk_number: 'BBBBBB',
                        stk_description: 'BBBBBB',
                        stk_comment: 'BBBBBB',
                        root_cause_description: 'BBBBBB',
                        rca_fix_success: 'BBBBBB',
                        stk_start_date: currentDate.format(DATE_TIME_FORMAT),
                        requested_date: currentDate.format(DATE_TIME_FORMAT),
                        responded_time: currentDate.format(DATE_TIME_FORMAT),
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        nttdata_start_date: currentDate.format(DATE_TIME_FORMAT),
                        rca_completed_date: currentDate.format(DATE_TIME_FORMAT),
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
                        live_date: currentDate.format(DATE_TIME_FORMAT),
                        km1: 'BBBBBB',
                        km1_breached: true,
                        km2: 'BBBBBB',
                        km2_breached: true,
                        km3: 'BBBBBB',
                        km3_breached: true,
                        qm1: 'BBBBBB',
                        qm1_breached: true,
                        qm2: 'BBBBBB',
                        qm2_breached: true,
                        qm3: 'BBBBBB',
                        qm3_breached: true,
                        qm4: 'BBBBBB',
                        qm4_breached: true,
                        rca_completed: true,
                        solution_found: true,
                        finaldate_rca: currentDate.format(DATE_TIME_FORMAT),
                        finaldate_solution: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        stk_start_date: currentDate,
                        requested_date: currentDate,
                        responded_time: currentDate,
                        modified_time: currentDate,
                        nttdata_start_date: currentDate,
                        rca_completed_date: currentDate,
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
                        live_date: currentDate,
                        finaldate_rca: currentDate,
                        finaldate_solution: currentDate
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

            it('should delete a Stk', async () => {
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
