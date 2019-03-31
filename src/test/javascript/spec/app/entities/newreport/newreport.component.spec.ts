/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { NewreportComponent } from 'app/entities/newreport/newreport.component';
import { NewreportService } from 'app/entities/newreport/newreport.service';
import { Newreport } from 'app/shared/model/newreport.model';

describe('Component Tests', () => {
    describe('Newreport Management Component', () => {
        let comp: NewreportComponent;
        let fixture: ComponentFixture<NewreportComponent>;
        let service: NewreportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [NewreportComponent],
                providers: []
            })
                .overrideTemplate(NewreportComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NewreportComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NewreportService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Newreport(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.newreports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
