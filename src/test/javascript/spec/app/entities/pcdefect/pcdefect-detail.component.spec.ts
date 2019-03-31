/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { PcdefectDetailComponent } from 'app/entities/pcdefect/pcdefect-detail.component';
import { Pcdefect } from 'app/shared/model/pcdefect.model';

describe('Component Tests', () => {
    describe('Pcdefect Management Detail Component', () => {
        let comp: PcdefectDetailComponent;
        let fixture: ComponentFixture<PcdefectDetailComponent>;
        const route = ({ data: of({ pcdefect: new Pcdefect(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [PcdefectDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PcdefectDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PcdefectDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pcdefect).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
