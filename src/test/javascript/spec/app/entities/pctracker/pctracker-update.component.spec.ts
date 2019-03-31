/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { PctrackerUpdateComponent } from 'app/entities/pctracker/pctracker-update.component';
import { PctrackerService } from 'app/entities/pctracker/pctracker.service';
import { Pctracker } from 'app/shared/model/pctracker.model';

describe('Component Tests', () => {
    describe('Pctracker Management Update Component', () => {
        let comp: PctrackerUpdateComponent;
        let fixture: ComponentFixture<PctrackerUpdateComponent>;
        let service: PctrackerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [PctrackerUpdateComponent]
            })
                .overrideTemplate(PctrackerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PctrackerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PctrackerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Pctracker(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pctracker = entity;
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
                    const entity = new Pctracker();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pctracker = entity;
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
