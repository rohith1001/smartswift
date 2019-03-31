/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { Development_trackerDetailComponent } from 'app/entities/development-tracker/development-tracker-detail.component';
import { Development_tracker } from 'app/shared/model/development-tracker.model';

describe('Component Tests', () => {
    describe('Development_tracker Management Detail Component', () => {
        let comp: Development_trackerDetailComponent;
        let fixture: ComponentFixture<Development_trackerDetailComponent>;
        const route = ({ data: of({ development_tracker: new Development_tracker(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [Development_trackerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(Development_trackerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(Development_trackerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.development_tracker).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
