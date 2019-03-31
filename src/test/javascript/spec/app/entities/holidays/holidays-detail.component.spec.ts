/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { HolidaysDetailComponent } from 'app/entities/holidays/holidays-detail.component';
import { Holidays } from 'app/shared/model/holidays.model';

describe('Component Tests', () => {
    describe('Holidays Management Detail Component', () => {
        let comp: HolidaysDetailComponent;
        let fixture: ComponentFixture<HolidaysDetailComponent>;
        const route = ({ data: of({ holidays: new Holidays(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [HolidaysDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HolidaysDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HolidaysDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.holidays).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
