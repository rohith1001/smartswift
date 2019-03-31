/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { ConfigslasComponent } from 'app/entities/configslas/configslas.component';
import { ConfigslasService } from 'app/entities/configslas/configslas.service';
import { Configslas } from 'app/shared/model/configslas.model';

describe('Component Tests', () => {
    describe('Configslas Management Component', () => {
        let comp: ConfigslasComponent;
        let fixture: ComponentFixture<ConfigslasComponent>;
        let service: ConfigslasService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ConfigslasComponent],
                providers: []
            })
                .overrideTemplate(ConfigslasComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConfigslasComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfigslasService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Configslas(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.configslas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
