/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { ReportsDetailComponent } from 'app/entities/reports/reports-detail.component';
import { Reports } from 'app/shared/model/reports.model';

describe('Component Tests', () => {
    describe('Reports Management Detail Component', () => {
        let comp: ReportsDetailComponent;
        let fixture: ComponentFixture<ReportsDetailComponent>;
        const route = ({ data: of({ reports: new Reports(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ReportsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReportsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReportsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.reports).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
