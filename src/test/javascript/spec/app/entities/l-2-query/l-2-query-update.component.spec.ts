/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { L2queryUpdateComponent } from 'app/entities/l-2-query/l-2-query-update.component';
import { L2queryService } from 'app/entities/l-2-query/l-2-query.service';
import { L2query } from 'app/shared/model/l-2-query.model';

describe('Component Tests', () => {
    describe('L2query Management Update Component', () => {
        let comp: L2queryUpdateComponent;
        let fixture: ComponentFixture<L2queryUpdateComponent>;
        let service: L2queryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [L2queryUpdateComponent]
            })
                .overrideTemplate(L2queryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(L2queryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(L2queryService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new L2query(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.l2query = entity;
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
                    const entity = new L2query();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.l2query = entity;
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
