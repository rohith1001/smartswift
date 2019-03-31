/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { Pc_trackerService } from 'app/entities/pc-tracker/pc-tracker.service';
import { IPc_tracker, Pc_tracker } from 'app/shared/model/pc-tracker.model';

describe('Service Tests', () => {
    describe('Pc_tracker Service', () => {
        let injector: TestBed;
        let service: Pc_trackerService;
        let httpMock: HttpTestingController;
        let elemDefault: IPc_tracker;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(Pc_trackerService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Pc_tracker(
                0,
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
                'AAAAAAA',
                currentDate,
                0,
                0,
                0
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        date_received: currentDate.format(DATE_TIME_FORMAT),
                        iia_delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        iia_delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        dcd_delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        dcd_delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        wr_acknowledge_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        wr_acknowledge_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        wr_costready_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        wr_costready_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        hlcd_delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        hlcd_delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        test_ready_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        test_ready_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        launch_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        launch_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        modified_time: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a Pc_tracker', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        date_received: currentDate.format(DATE_TIME_FORMAT),
                        iia_delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        iia_delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        dcd_delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        dcd_delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        wr_acknowledge_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        wr_acknowledge_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        wr_costready_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        wr_costready_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        hlcd_delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        hlcd_delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        test_ready_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        test_ready_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        launch_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        launch_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        modified_time: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        date_received: currentDate,
                        iia_delivery_date_planned: currentDate,
                        iia_delivery_date_actual: currentDate,
                        dcd_delivery_date_planned: currentDate,
                        dcd_delivery_date_actual: currentDate,
                        wr_acknowledge_date_planned: currentDate,
                        wr_acknowledge_date_actual: currentDate,
                        wr_costready_date_planned: currentDate,
                        wr_costready_date_actual: currentDate,
                        hlcd_delivery_date_planned: currentDate,
                        hlcd_delivery_date_actual: currentDate,
                        test_ready_date_planned: currentDate,
                        test_ready_date_actual: currentDate,
                        launch_date_planned: currentDate,
                        launch_date_actual: currentDate,
                        delivery_date_planned: currentDate,
                        delivery_date_actual: currentDate,
                        modified_time: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Pc_tracker(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Pc_tracker', async () => {
                const returnedFromService = Object.assign(
                    {
                        elf_id: 'BBBBBB',
                        title: 'BBBBBB',
                        system: 'BBBBBB',
                        date_received: currentDate.format(DATE_TIME_FORMAT),
                        iia_delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        iia_delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        dcd_delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        dcd_delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        wr_acknowledge_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        wr_acknowledge_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        wr_costready_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        wr_costready_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        hlcd_delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        hlcd_delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        test_ready_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        test_ready_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        launch_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        launch_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        comments: 'BBBBBB',
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        major: 1,
                        minor: 1,
                        cosmetic: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        date_received: currentDate,
                        iia_delivery_date_planned: currentDate,
                        iia_delivery_date_actual: currentDate,
                        dcd_delivery_date_planned: currentDate,
                        dcd_delivery_date_actual: currentDate,
                        wr_acknowledge_date_planned: currentDate,
                        wr_acknowledge_date_actual: currentDate,
                        wr_costready_date_planned: currentDate,
                        wr_costready_date_actual: currentDate,
                        hlcd_delivery_date_planned: currentDate,
                        hlcd_delivery_date_actual: currentDate,
                        test_ready_date_planned: currentDate,
                        test_ready_date_actual: currentDate,
                        launch_date_planned: currentDate,
                        launch_date_actual: currentDate,
                        delivery_date_planned: currentDate,
                        delivery_date_actual: currentDate,
                        modified_time: currentDate
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

            it('should return a list of Pc_tracker', async () => {
                const returnedFromService = Object.assign(
                    {
                        elf_id: 'BBBBBB',
                        title: 'BBBBBB',
                        system: 'BBBBBB',
                        date_received: currentDate.format(DATE_TIME_FORMAT),
                        iia_delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        iia_delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        dcd_delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        dcd_delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        wr_acknowledge_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        wr_acknowledge_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        wr_costready_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        wr_costready_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        hlcd_delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        hlcd_delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        test_ready_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        test_ready_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        launch_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        launch_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        delivery_date_planned: currentDate.format(DATE_TIME_FORMAT),
                        delivery_date_actual: currentDate.format(DATE_TIME_FORMAT),
                        comments: 'BBBBBB',
                        modified_time: currentDate.format(DATE_TIME_FORMAT),
                        major: 1,
                        minor: 1,
                        cosmetic: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        date_received: currentDate,
                        iia_delivery_date_planned: currentDate,
                        iia_delivery_date_actual: currentDate,
                        dcd_delivery_date_planned: currentDate,
                        dcd_delivery_date_actual: currentDate,
                        wr_acknowledge_date_planned: currentDate,
                        wr_acknowledge_date_actual: currentDate,
                        wr_costready_date_planned: currentDate,
                        wr_costready_date_actual: currentDate,
                        hlcd_delivery_date_planned: currentDate,
                        hlcd_delivery_date_actual: currentDate,
                        test_ready_date_planned: currentDate,
                        test_ready_date_actual: currentDate,
                        launch_date_planned: currentDate,
                        launch_date_actual: currentDate,
                        delivery_date_planned: currentDate,
                        delivery_date_actual: currentDate,
                        modified_time: currentDate
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

            it('should delete a Pc_tracker', async () => {
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
