/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { Elf_statusUpdateComponent } from 'app/entities/elf-status/elf-status-update.component';
import { Elf_statusService } from 'app/entities/elf-status/elf-status.service';
import { Elf_status } from 'app/shared/model/elf-status.model';

describe('Component Tests', () => {
    describe('Elf_status Management Update Component', () => {
        let comp: Elf_statusUpdateComponent;
        let fixture: ComponentFixture<Elf_statusUpdateComponent>;
        let service: Elf_statusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [Elf_statusUpdateComponent]
            })
                .overrideTemplate(Elf_statusUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(Elf_statusUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Elf_statusService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Elf_status(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.elf_status = entity;
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
                    const entity = new Elf_status();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.elf_status = entity;
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
