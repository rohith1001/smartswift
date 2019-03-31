/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { ResolvedComponent } from 'app/entities/resolved/resolved.component';
import { ResolvedService } from 'app/entities/resolved/resolved.service';
import { Resolved } from 'app/shared/model/resolved.model';

describe('Component Tests', () => {
    describe('Resolved Management Component', () => {
        let comp: ResolvedComponent;
        let fixture: ComponentFixture<ResolvedComponent>;
        let service: ResolvedService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ResolvedComponent],
                providers: []
            })
                .overrideTemplate(ResolvedComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ResolvedComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResolvedService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Resolved(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.resolveds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
