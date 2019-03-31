/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { Pc_trackerComponent } from 'app/entities/pc-tracker/pc-tracker.component';
import { Pc_trackerService } from 'app/entities/pc-tracker/pc-tracker.service';
import { Pc_tracker } from 'app/shared/model/pc-tracker.model';

describe('Component Tests', () => {
    describe('Pc_tracker Management Component', () => {
        let comp: Pc_trackerComponent;
        let fixture: ComponentFixture<Pc_trackerComponent>;
        let service: Pc_trackerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [Pc_trackerComponent],
                providers: []
            })
                .overrideTemplate(Pc_trackerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(Pc_trackerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Pc_trackerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Pc_tracker(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.pc_trackers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
