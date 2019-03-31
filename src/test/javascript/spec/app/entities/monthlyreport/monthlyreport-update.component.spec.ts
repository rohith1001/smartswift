/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { MonthlyreportUpdateComponent } from 'app/entities/monthlyreport/monthlyreport-update.component';
import { MonthlyreportService } from 'app/entities/monthlyreport/monthlyreport.service';
import { Monthlyreport } from 'app/shared/model/monthlyreport.model';

describe('Component Tests', () => {
    describe('Monthlyreport Management Update Component', () => {
        let comp: MonthlyreportUpdateComponent;
        let fixture: ComponentFixture<MonthlyreportUpdateComponent>;
        let service: MonthlyreportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [MonthlyreportUpdateComponent]
            })
                .overrideTemplate(MonthlyreportUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MonthlyreportUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MonthlyreportService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Monthlyreport(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.monthlyreport = entity;
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
                    const entity = new Monthlyreport();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.monthlyreport = entity;
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
