/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { DevserviceUpdateComponent } from 'app/entities/devservice/devservice-update.component';
import { DevserviceService } from 'app/entities/devservice/devservice.service';
import { Devservice } from 'app/shared/model/devservice.model';

describe('Component Tests', () => {
    describe('Devservice Management Update Component', () => {
        let comp: DevserviceUpdateComponent;
        let fixture: ComponentFixture<DevserviceUpdateComponent>;
        let service: DevserviceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [DevserviceUpdateComponent]
            })
                .overrideTemplate(DevserviceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DevserviceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DevserviceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Devservice(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.devservice = entity;
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
                    const entity = new Devservice();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.devservice = entity;
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
