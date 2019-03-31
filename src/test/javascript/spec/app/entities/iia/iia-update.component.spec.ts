/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { IiaUpdateComponent } from 'app/entities/iia/iia-update.component';
import { IiaService } from 'app/entities/iia/iia.service';
import { Iia } from 'app/shared/model/iia.model';

describe('Component Tests', () => {
    describe('Iia Management Update Component', () => {
        let comp: IiaUpdateComponent;
        let fixture: ComponentFixture<IiaUpdateComponent>;
        let service: IiaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [IiaUpdateComponent]
            })
                .overrideTemplate(IiaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IiaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IiaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Iia(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.iia = entity;
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
                    const entity = new Iia();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.iia = entity;
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
