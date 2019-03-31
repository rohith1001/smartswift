/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { BulkdefectComponent } from 'app/entities/bulkdefect/bulkdefect.component';
import { BulkdefectService } from 'app/entities/bulkdefect/bulkdefect.service';
import { Bulkdefect } from 'app/shared/model/bulkdefect.model';

describe('Component Tests', () => {
    describe('Bulkdefect Management Component', () => {
        let comp: BulkdefectComponent;
        let fixture: ComponentFixture<BulkdefectComponent>;
        let service: BulkdefectService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [BulkdefectComponent],
                providers: []
            })
                .overrideTemplate(BulkdefectComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BulkdefectComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BulkdefectService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Bulkdefect(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bulkdefects[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
