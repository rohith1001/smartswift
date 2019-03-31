/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { ImpactDetailComponent } from 'app/entities/impact/impact-detail.component';
import { Impact } from 'app/shared/model/impact.model';

describe('Component Tests', () => {
    describe('Impact Management Detail Component', () => {
        let comp: ImpactDetailComponent;
        let fixture: ComponentFixture<ImpactDetailComponent>;
        const route = ({ data: of({ impact: new Impact(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ImpactDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ImpactDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ImpactDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.impact).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
