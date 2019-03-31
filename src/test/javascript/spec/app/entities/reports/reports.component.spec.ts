/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { ReportsComponent } from 'app/entities/reports/reports.component';
import { ReportsService } from 'app/entities/reports/reports.service';
import { Reports } from 'app/shared/model/reports.model';

describe('Component Tests', () => {
    describe('Reports Management Component', () => {
        let comp: ReportsComponent;
        let fixture: ComponentFixture<ReportsComponent>;
        let service: ReportsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ReportsComponent],
                providers: []
            })
                .overrideTemplate(ReportsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReportsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReportsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Reports(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.reports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
