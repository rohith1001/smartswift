/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { SamplebulkUpdateComponent } from 'app/entities/samplebulk/samplebulk-update.component';
import { SamplebulkService } from 'app/entities/samplebulk/samplebulk.service';
import { Samplebulk } from 'app/shared/model/samplebulk.model';

describe('Component Tests', () => {
    describe('Samplebulk Management Update Component', () => {
        let comp: SamplebulkUpdateComponent;
        let fixture: ComponentFixture<SamplebulkUpdateComponent>;
        let service: SamplebulkService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [SamplebulkUpdateComponent]
            })
                .overrideTemplate(SamplebulkUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SamplebulkUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SamplebulkService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Samplebulk(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.samplebulk = entity;
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
                    const entity = new Samplebulk();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.samplebulk = entity;
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
