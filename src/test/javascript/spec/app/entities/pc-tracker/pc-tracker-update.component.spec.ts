/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { Pc_trackerUpdateComponent } from 'app/entities/pc-tracker/pc-tracker-update.component';
import { Pc_trackerService } from 'app/entities/pc-tracker/pc-tracker.service';
import { Pc_tracker } from 'app/shared/model/pc-tracker.model';

describe('Component Tests', () => {
    describe('Pc_tracker Management Update Component', () => {
        let comp: Pc_trackerUpdateComponent;
        let fixture: ComponentFixture<Pc_trackerUpdateComponent>;
        let service: Pc_trackerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [Pc_trackerUpdateComponent]
            })
                .overrideTemplate(Pc_trackerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(Pc_trackerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Pc_trackerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Pc_tracker(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pc_tracker = entity;
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
                    const entity = new Pc_tracker();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pc_tracker = entity;
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
