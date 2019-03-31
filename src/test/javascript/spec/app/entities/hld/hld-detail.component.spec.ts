/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { HldDetailComponent } from 'app/entities/hld/hld-detail.component';
import { Hld } from 'app/shared/model/hld.model';

describe('Component Tests', () => {
    describe('Hld Management Detail Component', () => {
        let comp: HldDetailComponent;
        let fixture: ComponentFixture<HldDetailComponent>;
        const route = ({ data: of({ hld: new Hld(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [HldDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HldDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HldDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.hld).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
