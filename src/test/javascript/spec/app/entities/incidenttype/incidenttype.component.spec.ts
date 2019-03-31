/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { IncidenttypeComponent } from 'app/entities/incidenttype/incidenttype.component';
import { IncidenttypeService } from 'app/entities/incidenttype/incidenttype.service';
import { Incidenttype } from 'app/shared/model/incidenttype.model';

describe('Component Tests', () => {
    describe('Incidenttype Management Component', () => {
        let comp: IncidenttypeComponent;
        let fixture: ComponentFixture<IncidenttypeComponent>;
        let service: IncidenttypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [IncidenttypeComponent],
                providers: []
            })
                .overrideTemplate(IncidenttypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IncidenttypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IncidenttypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Incidenttype(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.incidenttypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
