/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { BulkreleaseUpdateComponent } from 'app/entities/bulkrelease/bulkrelease-update.component';
import { BulkreleaseService } from 'app/entities/bulkrelease/bulkrelease.service';
import { Bulkrelease } from 'app/shared/model/bulkrelease.model';

describe('Component Tests', () => {
    describe('Bulkrelease Management Update Component', () => {
        let comp: BulkreleaseUpdateComponent;
        let fixture: ComponentFixture<BulkreleaseUpdateComponent>;
        let service: BulkreleaseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [BulkreleaseUpdateComponent]
            })
                .overrideTemplate(BulkreleaseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BulkreleaseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BulkreleaseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Bulkrelease(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bulkrelease = entity;
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
                    const entity = new Bulkrelease();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bulkrelease = entity;
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
