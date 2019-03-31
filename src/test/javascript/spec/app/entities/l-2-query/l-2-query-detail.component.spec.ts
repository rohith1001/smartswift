/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { L2queryDetailComponent } from 'app/entities/l-2-query/l-2-query-detail.component';
import { L2query } from 'app/shared/model/l-2-query.model';

describe('Component Tests', () => {
    describe('L2query Management Detail Component', () => {
        let comp: L2queryDetailComponent;
        let fixture: ComponentFixture<L2queryDetailComponent>;
        const route = ({ data: of({ l2query: new L2query(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [L2queryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(L2queryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(L2queryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.l2query).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
