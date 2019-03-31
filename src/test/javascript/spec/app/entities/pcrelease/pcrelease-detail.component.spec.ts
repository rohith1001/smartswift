/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { PcreleaseDetailComponent } from 'app/entities/pcrelease/pcrelease-detail.component';
import { Pcrelease } from 'app/shared/model/pcrelease.model';

describe('Component Tests', () => {
    describe('Pcrelease Management Detail Component', () => {
        let comp: PcreleaseDetailComponent;
        let fixture: ComponentFixture<PcreleaseDetailComponent>;
        const route = ({ data: of({ pcrelease: new Pcrelease(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [PcreleaseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PcreleaseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PcreleaseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pcrelease).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
