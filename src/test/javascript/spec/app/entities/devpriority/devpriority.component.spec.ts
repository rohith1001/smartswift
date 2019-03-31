/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { DevpriorityComponent } from 'app/entities/devpriority/devpriority.component';
import { DevpriorityService } from 'app/entities/devpriority/devpriority.service';
import { Devpriority } from 'app/shared/model/devpriority.model';

describe('Component Tests', () => {
    describe('Devpriority Management Component', () => {
        let comp: DevpriorityComponent;
        let fixture: ComponentFixture<DevpriorityComponent>;
        let service: DevpriorityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [DevpriorityComponent],
                providers: []
            })
                .overrideTemplate(DevpriorityComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DevpriorityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DevpriorityService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Devpriority(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.devpriorities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
