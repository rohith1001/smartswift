/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { Test_trackerUpdateComponent } from 'app/entities/test-tracker/test-tracker-update.component';
import { Test_trackerService } from 'app/entities/test-tracker/test-tracker.service';
import { Test_tracker } from 'app/shared/model/test-tracker.model';

describe('Component Tests', () => {
    describe('Test_tracker Management Update Component', () => {
        let comp: Test_trackerUpdateComponent;
        let fixture: ComponentFixture<Test_trackerUpdateComponent>;
        let service: Test_trackerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [Test_trackerUpdateComponent]
            })
                .overrideTemplate(Test_trackerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(Test_trackerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Test_trackerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Test_tracker(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.test_tracker = entity;
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
                    const entity = new Test_tracker();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.test_tracker = entity;
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
