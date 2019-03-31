/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { BulkincidentComponent } from 'app/entities/bulkincident/bulkincident.component';
import { BulkincidentService } from 'app/entities/bulkincident/bulkincident.service';
import { Bulkincident } from 'app/shared/model/bulkincident.model';

describe('Component Tests', () => {
    describe('Bulkincident Management Component', () => {
        let comp: BulkincidentComponent;
        let fixture: ComponentFixture<BulkincidentComponent>;
        let service: BulkincidentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [BulkincidentComponent],
                providers: []
            })
                .overrideTemplate(BulkincidentComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BulkincidentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BulkincidentService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Bulkincident(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bulkincidents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
