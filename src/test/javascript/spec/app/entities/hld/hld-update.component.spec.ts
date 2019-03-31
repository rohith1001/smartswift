/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { HldUpdateComponent } from 'app/entities/hld/hld-update.component';
import { HldService } from 'app/entities/hld/hld.service';
import { Hld } from 'app/shared/model/hld.model';

describe('Component Tests', () => {
    describe('Hld Management Update Component', () => {
        let comp: HldUpdateComponent;
        let fixture: ComponentFixture<HldUpdateComponent>;
        let service: HldService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [HldUpdateComponent]
            })
                .overrideTemplate(HldUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HldUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HldService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Hld(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hld = entity;
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
                    const entity = new Hld();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hld = entity;
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
