/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PcreleaseService } from 'app/entities/pcrelease/pcrelease.service';
import { IPcrelease, Pcrelease } from 'app/shared/model/pcrelease.model';

describe('Service Tests', () => {
    describe('Pcrelease Service', () => {
        let injector: TestBed;
        let service: PcreleaseService;
        let httpMock: HttpTestingController;
        let elemDefault: IPcrelease;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PcreleaseService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Pcrelease(0, 'AAAAAAA', currentDate, 'AAAAAAA', 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
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

            it('should create a Pcrelease', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        release_date: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        release_date: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Pcrelease(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Pcrelease', async () => {
                const returnedFromService = Object.assign(
                    {
                        system: 'BBBBBB',
                        release_date: currentDate.format(DATE_FORMAT),
                        testedbyamdocs: 'BBBBBB',
                        total_effort: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
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

            it('should return a list of Pcrelease', async () => {
                const returnedFromService = Object.assign(
                    {
                        system: 'BBBBBB',
                        release_date: currentDate.format(DATE_FORMAT),
                        testedbyamdocs: 'BBBBBB',
                        total_effort: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
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

            it('should delete a Pcrelease', async () => {
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
