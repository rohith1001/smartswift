/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { IssuetypeUpdateComponent } from 'app/entities/issuetype/issuetype-update.component';
import { IssuetypeService } from 'app/entities/issuetype/issuetype.service';
import { Issuetype } from 'app/shared/model/issuetype.model';

describe('Component Tests', () => {
    describe('Issuetype Management Update Component', () => {
        let comp: IssuetypeUpdateComponent;
        let fixture: ComponentFixture<IssuetypeUpdateComponent>;
        let service: IssuetypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [IssuetypeUpdateComponent]
            })
                .overrideTemplate(IssuetypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IssuetypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IssuetypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Issuetype(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.issuetype = entity;
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
                    const entity = new Issuetype();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.issuetype = entity;
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
