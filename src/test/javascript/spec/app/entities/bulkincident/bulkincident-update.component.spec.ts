/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { BulkincidentUpdateComponent } from 'app/entities/bulkincident/bulkincident-update.component';
import { BulkincidentService } from 'app/entities/bulkincident/bulkincident.service';
import { Bulkincident } from 'app/shared/model/bulkincident.model';

describe('Component Tests', () => {
    describe('Bulkincident Management Update Component', () => {
        let comp: BulkincidentUpdateComponent;
        let fixture: ComponentFixture<BulkincidentUpdateComponent>;
        let service: BulkincidentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [BulkincidentUpdateComponent]
            })
                .overrideTemplate(BulkincidentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BulkincidentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BulkincidentService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Bulkincident(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bulkincident = entity;
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
                    const entity = new Bulkincident();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bulkincident = entity;
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
