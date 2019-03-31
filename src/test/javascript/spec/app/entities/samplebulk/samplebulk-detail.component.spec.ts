/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { SamplebulkDetailComponent } from 'app/entities/samplebulk/samplebulk-detail.component';
import { Samplebulk } from 'app/shared/model/samplebulk.model';

describe('Component Tests', () => {
    describe('Samplebulk Management Detail Component', () => {
        let comp: SamplebulkDetailComponent;
        let fixture: ComponentFixture<SamplebulkDetailComponent>;
        const route = ({ data: of({ samplebulk: new Samplebulk(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [SamplebulkDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SamplebulkDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SamplebulkDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.samplebulk).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
