/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { Development_trackerComponent } from 'app/entities/development-tracker/development-tracker.component';
import { Development_trackerService } from 'app/entities/development-tracker/development-tracker.service';
import { Development_tracker } from 'app/shared/model/development-tracker.model';

describe('Component Tests', () => {
    describe('Development_tracker Management Component', () => {
        let comp: Development_trackerComponent;
        let fixture: ComponentFixture<Development_trackerComponent>;
        let service: Development_trackerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [Development_trackerComponent],
                providers: []
            })
                .overrideTemplate(Development_trackerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(Development_trackerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Development_trackerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Development_tracker(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.development_trackers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
