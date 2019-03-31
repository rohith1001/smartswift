/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { ResolvedUpdateComponent } from 'app/entities/resolved/resolved-update.component';
import { ResolvedService } from 'app/entities/resolved/resolved.service';
import { Resolved } from 'app/shared/model/resolved.model';

describe('Component Tests', () => {
    describe('Resolved Management Update Component', () => {
        let comp: ResolvedUpdateComponent;
        let fixture: ComponentFixture<ResolvedUpdateComponent>;
        let service: ResolvedService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ResolvedUpdateComponent]
            })
                .overrideTemplate(ResolvedUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ResolvedUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResolvedService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Resolved(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.resolved = entity;
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
                    const entity = new Resolved();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.resolved = entity;
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
