/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { IncidenttypeUpdateComponent } from 'app/entities/incidenttype/incidenttype-update.component';
import { IncidenttypeService } from 'app/entities/incidenttype/incidenttype.service';
import { Incidenttype } from 'app/shared/model/incidenttype.model';

describe('Component Tests', () => {
    describe('Incidenttype Management Update Component', () => {
        let comp: IncidenttypeUpdateComponent;
        let fixture: ComponentFixture<IncidenttypeUpdateComponent>;
        let service: IncidenttypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [IncidenttypeUpdateComponent]
            })
                .overrideTemplate(IncidenttypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IncidenttypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IncidenttypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Incidenttype(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.incidenttype = entity;
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
                    const entity = new Incidenttype();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.incidenttype = entity;
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
