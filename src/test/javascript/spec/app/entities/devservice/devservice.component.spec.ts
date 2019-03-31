/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { DevserviceComponent } from 'app/entities/devservice/devservice.component';
import { DevserviceService } from 'app/entities/devservice/devservice.service';
import { Devservice } from 'app/shared/model/devservice.model';

describe('Component Tests', () => {
    describe('Devservice Management Component', () => {
        let comp: DevserviceComponent;
        let fixture: ComponentFixture<DevserviceComponent>;
        let service: DevserviceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [DevserviceComponent],
                providers: []
            })
                .overrideTemplate(DevserviceComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DevserviceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DevserviceService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Devservice(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.devservices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
