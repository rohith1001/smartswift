/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { NewreportUpdateComponent } from 'app/entities/newreport/newreport-update.component';
import { NewreportService } from 'app/entities/newreport/newreport.service';
import { Newreport } from 'app/shared/model/newreport.model';

describe('Component Tests', () => {
    describe('Newreport Management Update Component', () => {
        let comp: NewreportUpdateComponent;
        let fixture: ComponentFixture<NewreportUpdateComponent>;
        let service: NewreportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [NewreportUpdateComponent]
            })
                .overrideTemplate(NewreportUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NewreportUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NewreportService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Newreport(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.newreport = entity;
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
                    const entity = new Newreport();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.newreport = entity;
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
