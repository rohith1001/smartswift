/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { PcdefectUpdateComponent } from 'app/entities/pcdefect/pcdefect-update.component';
import { PcdefectService } from 'app/entities/pcdefect/pcdefect.service';
import { Pcdefect } from 'app/shared/model/pcdefect.model';

describe('Component Tests', () => {
    describe('Pcdefect Management Update Component', () => {
        let comp: PcdefectUpdateComponent;
        let fixture: ComponentFixture<PcdefectUpdateComponent>;
        let service: PcdefectService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [PcdefectUpdateComponent]
            })
                .overrideTemplate(PcdefectUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PcdefectUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PcdefectService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Pcdefect(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pcdefect = entity;
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
                    const entity = new Pcdefect();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pcdefect = entity;
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
