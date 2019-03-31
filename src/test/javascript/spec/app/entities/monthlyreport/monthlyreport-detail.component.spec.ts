/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { MonthlyreportDetailComponent } from 'app/entities/monthlyreport/monthlyreport-detail.component';
import { Monthlyreport } from 'app/shared/model/monthlyreport.model';

describe('Component Tests', () => {
    describe('Monthlyreport Management Detail Component', () => {
        let comp: MonthlyreportDetailComponent;
        let fixture: ComponentFixture<MonthlyreportDetailComponent>;
        const route = ({ data: of({ monthlyreport: new Monthlyreport(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [MonthlyreportDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MonthlyreportDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MonthlyreportDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.monthlyreport).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
