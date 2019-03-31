/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { PcincidentComponent } from 'app/entities/pcincident/pcincident.component';
import { PcincidentService } from 'app/entities/pcincident/pcincident.service';
import { Pcincident } from 'app/shared/model/pcincident.model';

describe('Component Tests', () => {
    describe('Pcincident Management Component', () => {
        let comp: PcincidentComponent;
        let fixture: ComponentFixture<PcincidentComponent>;
        let service: PcincidentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [PcincidentComponent],
                providers: []
            })
                .overrideTemplate(PcincidentComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PcincidentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PcincidentService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Pcincident(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.pcincidents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
