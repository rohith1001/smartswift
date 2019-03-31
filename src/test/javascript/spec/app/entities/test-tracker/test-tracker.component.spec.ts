/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { Test_trackerComponent } from 'app/entities/test-tracker/test-tracker.component';
import { Test_trackerService } from 'app/entities/test-tracker/test-tracker.service';
import { Test_tracker } from 'app/shared/model/test-tracker.model';

describe('Component Tests', () => {
    describe('Test_tracker Management Component', () => {
        let comp: Test_trackerComponent;
        let fixture: ComponentFixture<Test_trackerComponent>;
        let service: Test_trackerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [Test_trackerComponent],
                providers: []
            })
                .overrideTemplate(Test_trackerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(Test_trackerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Test_trackerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Test_tracker(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.test_trackers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
