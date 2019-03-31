/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { PcincidentUpdateComponent } from 'app/entities/pcincident/pcincident-update.component';
import { PcincidentService } from 'app/entities/pcincident/pcincident.service';
import { Pcincident } from 'app/shared/model/pcincident.model';

describe('Component Tests', () => {
    describe('Pcincident Management Update Component', () => {
        let comp: PcincidentUpdateComponent;
        let fixture: ComponentFixture<PcincidentUpdateComponent>;
        let service: PcincidentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [PcincidentUpdateComponent]
            })
                .overrideTemplate(PcincidentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PcincidentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PcincidentService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Pcincident(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pcincident = entity;
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
                    const entity = new Pcincident();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pcincident = entity;
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
