/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { StkDetailComponent } from 'app/entities/stk/stk-detail.component';
import { Stk } from 'app/shared/model/stk.model';

describe('Component Tests', () => {
    describe('Stk Management Detail Component', () => {
        let comp: StkDetailComponent;
        let fixture: ComponentFixture<StkDetailComponent>;
        const route = ({ data: of({ stk: new Stk(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [StkDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StkDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StkDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.stk).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
