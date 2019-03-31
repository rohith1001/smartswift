/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PcdefectService } from 'app/entities/pcdefect/pcdefect.service';
import { IPcdefect, Pcdefect } from 'app/shared/model/pcdefect.model';

describe('Service Tests', () => {
    describe('Pcdefect Service', () => {
        let injector: TestBed;
        let service: PcdefectService;
        let httpMock: HttpTestingController;
        let elemDefault: IPcdefect;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PcdefectService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Pcdefect(
                0,
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        date_raised: currentDate.format(DATE_FORMAT),
                        date_closed: currentDate.format(DATE_FORMAT),
                        release_date: currentDate.format(DATE_FORMAT)
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

            it('should create a Pcdefect', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        date_raised: currentDate.format(DATE_FORMAT),
                        date_closed: currentDate.format(DATE_FORMAT),
                        release_date: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        date_raised: currentDate,
                        date_closed: currentDate,
                        release_date: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Pcdefect(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Pcdefect', async () => {
                const returnedFromService = Object.assign(
                    {
                        defect_id: 1,
                        description: 'BBBBBB',
                        system_impacted: 'BBBBBB',
                        date_raised: currentDate.format(DATE_FORMAT),
                        severity: 'BBBBBB',
                        date_closed: currentDate.format(DATE_FORMAT),
                        request_id: 'BBBBBB',
                        release_date: currentDate.format(DATE_FORMAT),
                        comments: 'BBBBBB',
                        root_cause: 'BBBBBB',
                        user_id: 1,
                        testedbyamdocs: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        date_raised: currentDate,
                        date_closed: currentDate,
                        release_date: currentDate
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

            it('should return a list of Pcdefect', async () => {
                const returnedFromService = Object.assign(
                    {
                        defect_id: 1,
                        description: 'BBBBBB',
                        system_impacted: 'BBBBBB',
                        date_raised: currentDate.format(DATE_FORMAT),
                        severity: 'BBBBBB',
                        date_closed: currentDate.format(DATE_FORMAT),
                        request_id: 'BBBBBB',
                        release_date: currentDate.format(DATE_FORMAT),
                        comments: 'BBBBBB',
                        root_cause: 'BBBBBB',
                        user_id: 1,
                        testedbyamdocs: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        date_raised: currentDate,
                        date_closed: currentDate,
                        release_date: currentDate
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

            it('should delete a Pcdefect', async () => {
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
