/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { ConfigtypeComponent } from 'app/entities/configtype/configtype.component';
import { ConfigtypeService } from 'app/entities/configtype/configtype.service';
import { Configtype } from 'app/shared/model/configtype.model';

describe('Component Tests', () => {
    describe('Configtype Management Component', () => {
        let comp: ConfigtypeComponent;
        let fixture: ComponentFixture<ConfigtypeComponent>;
        let service: ConfigtypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ConfigtypeComponent],
                providers: []
            })
                .overrideTemplate(ConfigtypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConfigtypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfigtypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Configtype(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.configtypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
