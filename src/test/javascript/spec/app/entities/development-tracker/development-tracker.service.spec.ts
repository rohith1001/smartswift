/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { Development_trackerService } from 'app/entities/development-tracker/development-tracker.service';
import { IDevelopment_tracker, Development_tracker } from 'app/shared/model/development-tracker.model';

describe('Service Tests', () => {
    describe('Development_tracker Service', () => {
        let injector: TestBed;
        let service: Development_trackerService;
        let httpMock: HttpTestingController;
        let elemDefault: IDevelopment_tracker;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(Development_trackerService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Development_tracker(
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                0,
                0,
                'AAAAAAA',
                currentDate,
                currentDate,
                currentDate,
                'AAAAAAA',
                0,
                0,
                0,
                0,
                0,
                currentDate,
                currentDate,
                'AAAAAAA',
                0,
                0,
                0,
                0,
                0,
                0,
                currentDate,
                0,
                'AAAAAAA',
                'AAAAAAA',
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
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        request_date: currentDate.format(DATE_TIME_FORMAT),
                        ack_date: currentDate.format(DATE_TIME_FORMAT),
                        cost_ready_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        delivery_to_test_planned: currentDate.format(DATE_TIME_FORMAT),
                        delivery_to_test_actual: currentDate.format(DATE_TIME_FORMAT),
                        delivery_to_production_planned: currentDate.format(DATE_TIME_FORMAT),
                        delivery_to_production_actual: currentDate.format(DATE_TIME_FORMAT),
                        defect_date_raised: currentDate.format(DATE_TIME_FORMAT),
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        sow_submission_date: currentDate.format(DATE_TIME_FORMAT),
                        finaldate: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a Development_tracker', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        request_date: currentDate.format(DATE_TIME_FORMAT),
                        ack_date: currentDate.format(DATE_TIME_FORMAT),
                        cost_ready_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        delivery_to_test_planned: currentDate.format(DATE_TIME_FORMAT),
                        delivery_to_test_actual: currentDate.format(DATE_TIME_FORMAT),
                        delivery_to_production_planned: currentDate.format(DATE_TIME_FORMAT),
                        delivery_to_production_actual: currentDate.format(DATE_TIME_FORMAT),
                        defect_date_raised: currentDate.format(DATE_TIME_FORMAT),
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        sow_submission_date: currentDate.format(DATE_TIME_FORMAT),
                        finaldate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        request_date: currentDate,
                        ack_date: currentDate,
                        cost_ready_date_actual: currentDate,
                        delivery_to_test_planned: currentDate,
                        delivery_to_test_actual: currentDate,
                        delivery_to_production_planned: currentDate,
                        delivery_to_production_actual: currentDate,
                        defect_date_raised: currentDate,
                        modified_time: currentDate,
                        sow_submission_date: currentDate,
                        finaldate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Development_tracker(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Development_tracker', async () => {
                const returnedFromService = Object.assign(
                    {
                        elf_id: 'BBBBBB',
                        project_name: 'BBBBBB',
                        request_date: currentDate.format(DATE_TIME_FORMAT),
                        ack_date: currentDate.format(DATE_TIME_FORMAT),
                        actual_effort_design_development: 1,
                        actual_effort_development: 1,
                        estimated_development_cost_iia: 'BBBBBB',
                        cost_ready_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        delivery_to_test_planned: currentDate.format(DATE_TIME_FORMAT),
                        delivery_to_test_actual: currentDate.format(DATE_TIME_FORMAT),
                        test_completed: 'BBBBBB',
                        defect_detected_s1: 1,
                        defect_detected_s2: 1,
                        defect_detected_s3: 1,
                        defect_detected_s4: 1,
                        defect_detected_s5: 1,
                        delivery_to_production_planned: currentDate.format(DATE_TIME_FORMAT),
                        delivery_to_production_actual: currentDate.format(DATE_TIME_FORMAT),
                        implemented_successfully: 'BBBBBB',
                        incident_p1: 1,
                        incident_p2: 1,
                        incident_p3: 1,
                        incident_p4: 1,
                        incident_p5: 1,
                        incident_p6: 1,
                        defect_date_raised: currentDate.format(DATE_TIME_FORMAT),
                        critical_incident_s3_open: 1,
                        hold_status: 'BBBBBB',
                        comments: 'BBBBBB',
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        sow_submission_date: currentDate.format(DATE_TIME_FORMAT),
                        kpi1: 'BBBBBB',
                        kpi1_breached: true,
                        kpi2: 'BBBBBB',
                        kpi2_breached: true,
                        km1: 'BBBBBB',
                        km1_breached: true,
                        qm1: 'BBBBBB',
                        qm1_breached: true,
                        qm2: 'BBBBBB',
                        qm2_breached: true,
                        finaldate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        request_date: currentDate,
                        ack_date: currentDate,
                        cost_ready_date_actual: currentDate,
                        delivery_to_test_planned: currentDate,
                        delivery_to_test_actual: currentDate,
                        delivery_to_production_planned: currentDate,
                        delivery_to_production_actual: currentDate,
                        defect_date_raised: currentDate,
                        modified_time: currentDate,
                        sow_submission_date: currentDate,
                        finaldate: currentDate
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

            it('should return a list of Development_tracker', async () => {
                const returnedFromService = Object.assign(
                    {
                        elf_id: 'BBBBBB',
                        project_name: 'BBBBBB',
                        request_date: currentDate.format(DATE_TIME_FORMAT),
                        ack_date: currentDate.format(DATE_TIME_FORMAT),
                        actual_effort_design_development: 1,
                        actual_effort_development: 1,
                        estimated_development_cost_iia: 'BBBBBB',
                        cost_ready_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        delivery_to_test_planned: currentDate.format(DATE_TIME_FORMAT),
                        delivery_to_test_actual: currentDate.format(DATE_TIME_FORMAT),
                        test_completed: 'BBBBBB',
                        defect_detected_s1: 1,
                        defect_detected_s2: 1,
                        defect_detected_s3: 1,
                        defect_detected_s4: 1,
                        defect_detected_s5: 1,
                        delivery_to_production_planned: currentDate.format(DATE_TIME_FORMAT),
                        delivery_to_production_actual: currentDate.format(DATE_TIME_FORMAT),
                        implemented_successfully: 'BBBBBB',
                        incident_p1: 1,
                        incident_p2: 1,
                        incident_p3: 1,
                        incident_p4: 1,
                        incident_p5: 1,
                        incident_p6: 1,
                        defect_date_raised: currentDate.format(DATE_TIME_FORMAT),
                        critical_incident_s3_open: 1,
                        hold_status: 'BBBBBB',
                        comments: 'BBBBBB',
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        sow_submission_date: currentDate.format(DATE_TIME_FORMAT),
                        kpi1: 'BBBBBB',
                        kpi1_breached: true,
                        kpi2: 'BBBBBB',
                        kpi2_breached: true,
                        km1: 'BBBBBB',
                        km1_breached: true,
                        qm1: 'BBBBBB',
                        qm1_breached: true,
                        qm2: 'BBBBBB',
                        qm2_breached: true,
                        finaldate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        request_date: currentDate,
                        ack_date: currentDate,
                        cost_ready_date_actual: currentDate,
                        delivery_to_test_planned: currentDate,
                        delivery_to_test_actual: currentDate,
                        delivery_to_production_planned: currentDate,
                        delivery_to_production_actual: currentDate,
                        defect_date_raised: currentDate,
                        modified_time: currentDate,
                        sow_submission_date: currentDate,
                        finaldate: currentDate
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

            it('should delete a Development_tracker', async () => {
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
