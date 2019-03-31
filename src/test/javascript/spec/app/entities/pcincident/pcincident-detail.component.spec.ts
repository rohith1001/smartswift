/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { PcincidentDetailComponent } from 'app/entities/pcincident/pcincident-detail.component';
import { Pcincident } from 'app/shared/model/pcincident.model';

describe('Component Tests', () => {
    describe('Pcincident Management Detail Component', () => {
        let comp: PcincidentDetailComponent;
        let fixture: ComponentFixture<PcincidentDetailComponent>;
        const route = ({ data: of({ pcincident: new Pcincident(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [PcincidentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PcincidentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PcincidentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pcincident).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
