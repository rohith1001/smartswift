/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { PcreleaseUpdateComponent } from 'app/entities/pcrelease/pcrelease-update.component';
import { PcreleaseService } from 'app/entities/pcrelease/pcrelease.service';
import { Pcrelease } from 'app/shared/model/pcrelease.model';

describe('Component Tests', () => {
    describe('Pcrelease Management Update Component', () => {
        let comp: PcreleaseUpdateComponent;
        let fixture: ComponentFixture<PcreleaseUpdateComponent>;
        let service: PcreleaseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [PcreleaseUpdateComponent]
            })
                .overrideTemplate(PcreleaseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PcreleaseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PcreleaseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Pcrelease(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pcrelease = entity;
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
                    const entity = new Pcrelease();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pcrelease = entity;
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
