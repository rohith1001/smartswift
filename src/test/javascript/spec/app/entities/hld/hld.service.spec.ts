/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { HldService } from 'app/entities/hld/hld.service';
import { IHld, Hld } from 'app/shared/model/hld.model';

describe('Service Tests', () => {
    describe('Hld Service', () => {
        let injector: TestBed;
        let service: HldService;
        let httpMock: HttpTestingController;
        let elemDefault: IHld;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(HldService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Hld(
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                'AAAAAAA',
                currentDate,
                0,
                currentDate,
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                false,
                'AAAAAAA',
                false,
                0,
                'AAAAAAA',
                false,
                currentDate,
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        request_date: currentDate.format(DATE_TIME_FORMAT),
                        actual_acknowledgement_date: currentDate.format(DATE_TIME_FORMAT),
                        delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        agreed_date: currentDate.format(DATE_TIME_FORMAT),
                        hold_date: currentDate.format(DATE_TIME_FORMAT),
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        wif_submission_date: currentDate.format(DATE_TIME_FORMAT),
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

            it('should create a Hld', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        request_date: currentDate.format(DATE_TIME_FORMAT),
                        actual_acknowledgement_date: currentDate.format(DATE_TIME_FORMAT),
                        delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        agreed_date: currentDate.format(DATE_TIME_FORMAT),
                        hold_date: currentDate.format(DATE_TIME_FORMAT),
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        wif_submission_date: currentDate.format(DATE_TIME_FORMAT),
                        finaldate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        request_date: currentDate,
                        actual_acknowledgement_date: currentDate,
                        delivery_date_planned: currentDate,
                        delivery_date_actual: currentDate,
                        agreed_date: currentDate,
                        hold_date: currentDate,
                        modified_time: currentDate,
                        wif_submission_date: currentDate,
                        finaldate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Hld(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Hld', async () => {
                const returnedFromService = Object.assign(
                    {
                        elf_id: 'BBBBBB',
                        project_name: 'BBBBBB',
                        request_date: currentDate.format(DATE_TIME_FORMAT),
                        actual_acknowledgement_date: currentDate.format(DATE_TIME_FORMAT),
                        delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        agreed_date: currentDate.format(DATE_TIME_FORMAT),
                        hold_flag: 'BBBBBB',
                        hold_date: currentDate.format(DATE_TIME_FORMAT),
                        hold_days: 1,
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        comments: 'BBBBBB',
                        wif_submission_date: currentDate.format(DATE_TIME_FORMAT),
                        kpi1: 'BBBBBB',
                        kpi1_breached: true,
                        qm1: 'BBBBBB',
                        qm1_breached: true,
                        user_id: 1,
                        qm2: 'BBBBBB',
                        qm2_breached: true,
                        finaldate: currentDate.format(DATE_TIME_FORMAT),
                        requestor: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        request_date: currentDate,
                        actual_acknowledgement_date: currentDate,
                        delivery_date_planned: currentDate,
                        delivery_date_actual: currentDate,
                        agreed_date: currentDate,
                        hold_date: currentDate,
                        modified_time: currentDate,
                        wif_submission_date: currentDate,
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

            it('should return a list of Hld', async () => {
                const returnedFromService = Object.assign(
                    {
                        elf_id: 'BBBBBB',
                        project_name: 'BBBBBB',
                        request_date: currentDate.format(DATE_TIME_FORMAT),
                        actual_acknowledgement_date: currentDate.format(DATE_TIME_FORMAT),
                        delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        agreed_date: currentDate.format(DATE_TIME_FORMAT),
                        hold_flag: 'BBBBBB',
                        hold_date: currentDate.format(DATE_TIME_FORMAT),
                        hold_days: 1,
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        comments: 'BBBBBB',
                        wif_submission_date: currentDate.format(DATE_TIME_FORMAT),
                        kpi1: 'BBBBBB',
                        kpi1_breached: true,
                        qm1: 'BBBBBB',
                        qm1_breached: true,
                        user_id: 1,
                        qm2: 'BBBBBB',
                        qm2_breached: true,
                        finaldate: currentDate.format(DATE_TIME_FORMAT),
                        requestor: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        request_date: currentDate,
                        actual_acknowledgement_date: currentDate,
                        delivery_date_planned: currentDate,
                        delivery_date_actual: currentDate,
                        agreed_date: currentDate,
                        hold_date: currentDate,
                        modified_time: currentDate,
                        wif_submission_date: currentDate,
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

            it('should delete a Hld', async () => {
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
