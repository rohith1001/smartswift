/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { IiaDetailComponent } from 'app/entities/iia/iia-detail.component';
import { Iia } from 'app/shared/model/iia.model';

describe('Component Tests', () => {
    describe('Iia Management Detail Component', () => {
        let comp: IiaDetailComponent;
        let fixture: ComponentFixture<IiaDetailComponent>;
        const route = ({ data: of({ iia: new Iia(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [IiaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IiaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IiaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.iia).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
