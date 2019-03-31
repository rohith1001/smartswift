/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { NewreportDetailComponent } from 'app/entities/newreport/newreport-detail.component';
import { Newreport } from 'app/shared/model/newreport.model';

describe('Component Tests', () => {
    describe('Newreport Management Detail Component', () => {
        let comp: NewreportDetailComponent;
        let fixture: ComponentFixture<NewreportDetailComponent>;
        const route = ({ data: of({ newreport: new Newreport(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [NewreportDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(NewreportDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NewreportDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.newreport).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
