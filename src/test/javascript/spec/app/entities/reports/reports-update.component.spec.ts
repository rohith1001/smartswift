/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { ReportsUpdateComponent } from 'app/entities/reports/reports-update.component';
import { ReportsService } from 'app/entities/reports/reports.service';
import { Reports } from 'app/shared/model/reports.model';

describe('Component Tests', () => {
    describe('Reports Management Update Component', () => {
        let comp: ReportsUpdateComponent;
        let fixture: ComponentFixture<ReportsUpdateComponent>;
        let service: ReportsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ReportsUpdateComponent]
            })
                .overrideTemplate(ReportsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReportsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReportsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Reports(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reports = entity;
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
                    const entity = new Reports();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reports = entity;
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
