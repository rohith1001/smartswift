/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { IssuetypeComponent } from 'app/entities/issuetype/issuetype.component';
import { IssuetypeService } from 'app/entities/issuetype/issuetype.service';
import { Issuetype } from 'app/shared/model/issuetype.model';

describe('Component Tests', () => {
    describe('Issuetype Management Component', () => {
        let comp: IssuetypeComponent;
        let fixture: ComponentFixture<IssuetypeComponent>;
        let service: IssuetypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [IssuetypeComponent],
                providers: []
            })
                .overrideTemplate(IssuetypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IssuetypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IssuetypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Issuetype(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.issuetypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
