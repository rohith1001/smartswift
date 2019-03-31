/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { Pc_trackerDetailComponent } from 'app/entities/pc-tracker/pc-tracker-detail.component';
import { Pc_tracker } from 'app/shared/model/pc-tracker.model';

describe('Component Tests', () => {
    describe('Pc_tracker Management Detail Component', () => {
        let comp: Pc_trackerDetailComponent;
        let fixture: ComponentFixture<Pc_trackerDetailComponent>;
        const route = ({ data: of({ pc_tracker: new Pc_tracker(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [Pc_trackerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(Pc_trackerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(Pc_trackerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pc_tracker).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
