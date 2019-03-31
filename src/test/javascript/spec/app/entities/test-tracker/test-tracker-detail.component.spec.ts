/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { Test_trackerDetailComponent } from 'app/entities/test-tracker/test-tracker-detail.component';
import { Test_tracker } from 'app/shared/model/test-tracker.model';

describe('Component Tests', () => {
    describe('Test_tracker Management Detail Component', () => {
        let comp: Test_trackerDetailComponent;
        let fixture: ComponentFixture<Test_trackerDetailComponent>;
        const route = ({ data: of({ test_tracker: new Test_tracker(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [Test_trackerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(Test_trackerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(Test_trackerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.test_tracker).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
