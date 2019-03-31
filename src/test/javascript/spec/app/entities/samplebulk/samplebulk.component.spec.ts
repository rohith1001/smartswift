/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { SamplebulkComponent } from 'app/entities/samplebulk/samplebulk.component';
import { SamplebulkService } from 'app/entities/samplebulk/samplebulk.service';
import { Samplebulk } from 'app/shared/model/samplebulk.model';

describe('Component Tests', () => {
    describe('Samplebulk Management Component', () => {
        let comp: SamplebulkComponent;
        let fixture: ComponentFixture<SamplebulkComponent>;
        let service: SamplebulkService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [SamplebulkComponent],
                providers: []
            })
                .overrideTemplate(SamplebulkComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SamplebulkComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SamplebulkService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Samplebulk(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.samplebulks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
