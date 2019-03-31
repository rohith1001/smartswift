/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { MonthlyreportComponent } from 'app/entities/monthlyreport/monthlyreport.component';
import { MonthlyreportService } from 'app/entities/monthlyreport/monthlyreport.service';
import { Monthlyreport } from 'app/shared/model/monthlyreport.model';

describe('Component Tests', () => {
    describe('Monthlyreport Management Component', () => {
        let comp: MonthlyreportComponent;
        let fixture: ComponentFixture<MonthlyreportComponent>;
        let service: MonthlyreportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [MonthlyreportComponent],
                providers: []
            })
                .overrideTemplate(MonthlyreportComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MonthlyreportComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MonthlyreportService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Monthlyreport(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.monthlyreports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
