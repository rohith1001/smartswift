/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { IncidenttypeDetailComponent } from 'app/entities/incidenttype/incidenttype-detail.component';
import { Incidenttype } from 'app/shared/model/incidenttype.model';

describe('Component Tests', () => {
    describe('Incidenttype Management Detail Component', () => {
        let comp: IncidenttypeDetailComponent;
        let fixture: ComponentFixture<IncidenttypeDetailComponent>;
        const route = ({ data: of({ incidenttype: new Incidenttype(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [IncidenttypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IncidenttypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IncidenttypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.incidenttype).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
