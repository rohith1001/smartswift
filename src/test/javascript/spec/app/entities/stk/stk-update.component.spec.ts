/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { StkUpdateComponent } from 'app/entities/stk/stk-update.component';
import { StkService } from 'app/entities/stk/stk.service';
import { Stk } from 'app/shared/model/stk.model';

describe('Component Tests', () => {
    describe('Stk Management Update Component', () => {
        let comp: StkUpdateComponent;
        let fixture: ComponentFixture<StkUpdateComponent>;
        let service: StkService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [StkUpdateComponent]
            })
                .overrideTemplate(StkUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StkUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StkService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Stk(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stk = entity;
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
                    const entity = new Stk();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stk = entity;
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
