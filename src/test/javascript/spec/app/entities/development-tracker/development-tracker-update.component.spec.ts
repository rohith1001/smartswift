/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { Development_trackerUpdateComponent } from 'app/entities/development-tracker/development-tracker-update.component';
import { Development_trackerService } from 'app/entities/development-tracker/development-tracker.service';
import { Development_tracker } from 'app/shared/model/development-tracker.model';

describe('Component Tests', () => {
    describe('Development_tracker Management Update Component', () => {
        let comp: Development_trackerUpdateComponent;
        let fixture: ComponentFixture<Development_trackerUpdateComponent>;
        let service: Development_trackerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [Development_trackerUpdateComponent]
            })
                .overrideTemplate(Development_trackerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(Development_trackerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Development_trackerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Development_tracker(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.development_tracker = entity;
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
                    const entity = new Development_tracker();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.development_tracker = entity;
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
