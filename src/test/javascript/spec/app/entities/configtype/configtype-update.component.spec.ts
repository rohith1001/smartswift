/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { ConfigtypeUpdateComponent } from 'app/entities/configtype/configtype-update.component';
import { ConfigtypeService } from 'app/entities/configtype/configtype.service';
import { Configtype } from 'app/shared/model/configtype.model';

describe('Component Tests', () => {
    describe('Configtype Management Update Component', () => {
        let comp: ConfigtypeUpdateComponent;
        let fixture: ComponentFixture<ConfigtypeUpdateComponent>;
        let service: ConfigtypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ConfigtypeUpdateComponent]
            })
                .overrideTemplate(ConfigtypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConfigtypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfigtypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Configtype(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.configtype = entity;
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
                    const entity = new Configtype();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.configtype = entity;
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
