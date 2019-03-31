/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { DevpriorityUpdateComponent } from 'app/entities/devpriority/devpriority-update.component';
import { DevpriorityService } from 'app/entities/devpriority/devpriority.service';
import { Devpriority } from 'app/shared/model/devpriority.model';

describe('Component Tests', () => {
    describe('Devpriority Management Update Component', () => {
        let comp: DevpriorityUpdateComponent;
        let fixture: ComponentFixture<DevpriorityUpdateComponent>;
        let service: DevpriorityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [DevpriorityUpdateComponent]
            })
                .overrideTemplate(DevpriorityUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DevpriorityUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DevpriorityService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Devpriority(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.devpriority = entity;
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
                    const entity = new Devpriority();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.devpriority = entity;
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
