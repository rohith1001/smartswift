/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { HolidaysComponent } from 'app/entities/holidays/holidays.component';
import { HolidaysService } from 'app/entities/holidays/holidays.service';
import { Holidays } from 'app/shared/model/holidays.model';

describe('Component Tests', () => {
    describe('Holidays Management Component', () => {
        let comp: HolidaysComponent;
        let fixture: ComponentFixture<HolidaysComponent>;
        let service: HolidaysService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [HolidaysComponent],
                providers: []
            })
                .overrideTemplate(HolidaysComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HolidaysComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HolidaysService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Holidays(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.holidays[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
