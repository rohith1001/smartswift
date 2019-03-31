/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { BulkdefectUpdateComponent } from 'app/entities/bulkdefect/bulkdefect-update.component';
import { BulkdefectService } from 'app/entities/bulkdefect/bulkdefect.service';
import { Bulkdefect } from 'app/shared/model/bulkdefect.model';

describe('Component Tests', () => {
    describe('Bulkdefect Management Update Component', () => {
        let comp: BulkdefectUpdateComponent;
        let fixture: ComponentFixture<BulkdefectUpdateComponent>;
        let service: BulkdefectService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [BulkdefectUpdateComponent]
            })
                .overrideTemplate(BulkdefectUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BulkdefectUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BulkdefectService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Bulkdefect(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bulkdefect = entity;
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
                    const entity = new Bulkdefect();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bulkdefect = entity;
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
