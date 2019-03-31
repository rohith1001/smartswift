/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MonthlyreportService } from 'app/entities/monthlyreport/monthlyreport.service';
import { IMonthlyreport, Monthlyreport } from 'app/shared/model/monthlyreport.model';

describe('Service Tests', () => {
    describe('Monthlyreport Service', () => {
        let injector: TestBed;
        let service: MonthlyreportService;
        let httpMock: HttpTestingController;
        let elemDefault: IMonthlyreport;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(MonthlyreportService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Monthlyreport(0, currentDate, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        from_date: currentDate.format(DATE_FORMAT),
                        to_date: currentDate.format(DATE_FORMAT)
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

            it('should create a Monthlyreport', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        from_date: currentDate.format(DATE_FORMAT),
                        to_date: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        from_date: currentDate,
                        to_date: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Monthlyreport(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Monthlyreport', async () => {
                const returnedFromService = Object.assign(
                    {
                        from_date: currentDate.format(DATE_FORMAT),
                        to_date: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        from_date: currentDate,
                        to_date: currentDate
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

            it('should return a list of Monthlyreport', async () => {
                const returnedFromService = Object.assign(
                    {
                        from_date: currentDate.format(DATE_FORMAT),
                        to_date: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        from_date: currentDate,
                        to_date: currentDate
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

            it('should delete a Monthlyreport', async () => {
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
