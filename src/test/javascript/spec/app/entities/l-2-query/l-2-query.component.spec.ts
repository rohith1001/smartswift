/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { L2queryComponent } from 'app/entities/l-2-query/l-2-query.component';
import { L2queryService } from 'app/entities/l-2-query/l-2-query.service';
import { L2query } from 'app/shared/model/l-2-query.model';

describe('Component Tests', () => {
    describe('L2query Management Component', () => {
        let comp: L2queryComponent;
        let fixture: ComponentFixture<L2queryComponent>;
        let service: L2queryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [L2queryComponent],
                providers: []
            })
                .overrideTemplate(L2queryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(L2queryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(L2queryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new L2query(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.l2queries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
