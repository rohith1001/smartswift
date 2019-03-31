/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { ConfigslasUpdateComponent } from 'app/entities/configslas/configslas-update.component';
import { ConfigslasService } from 'app/entities/configslas/configslas.service';
import { Configslas } from 'app/shared/model/configslas.model';

describe('Component Tests', () => {
    describe('Configslas Management Update Component', () => {
        let comp: ConfigslasUpdateComponent;
        let fixture: ComponentFixture<ConfigslasUpdateComponent>;
        let service: ConfigslasService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ConfigslasUpdateComponent]
            })
                .overrideTemplate(ConfigslasUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConfigslasUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfigslasService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Configslas(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.configslas = entity;
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
                    const entity = new Configslas();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.configslas = entity;
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
