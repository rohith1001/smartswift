/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { ImpactUpdateComponent } from 'app/entities/impact/impact-update.component';
import { ImpactService } from 'app/entities/impact/impact.service';
import { Impact } from 'app/shared/model/impact.model';

describe('Component Tests', () => {
    describe('Impact Management Update Component', () => {
        let comp: ImpactUpdateComponent;
        let fixture: ComponentFixture<ImpactUpdateComponent>;
        let service: ImpactService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ImpactUpdateComponent]
            })
                .overrideTemplate(ImpactUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ImpactUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImpactService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Impact(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.impact = entity;
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
                    const entity = new Impact();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.impact = entity;
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
