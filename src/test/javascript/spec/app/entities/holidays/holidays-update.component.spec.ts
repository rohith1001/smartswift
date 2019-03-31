/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { HolidaysUpdateComponent } from 'app/entities/holidays/holidays-update.component';
import { HolidaysService } from 'app/entities/holidays/holidays.service';
import { Holidays } from 'app/shared/model/holidays.model';

describe('Component Tests', () => {
    describe('Holidays Management Update Component', () => {
        let comp: HolidaysUpdateComponent;
        let fixture: ComponentFixture<HolidaysUpdateComponent>;
        let service: HolidaysService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [HolidaysUpdateComponent]
            })
                .overrideTemplate(HolidaysUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HolidaysUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HolidaysService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Holidays(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.holidays = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Holidays();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.holidays = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
