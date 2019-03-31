/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { PcreleaseComponent } from 'app/entities/pcrelease/pcrelease.component';
import { PcreleaseService } from 'app/entities/pcrelease/pcrelease.service';
import { Pcrelease } from 'app/shared/model/pcrelease.model';

describe('Component Tests', () => {
    describe('Pcrelease Management Component', () => {
        let comp: PcreleaseComponent;
        let fixture: ComponentFixture<PcreleaseComponent>;
        let service: PcreleaseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [PcreleaseComponent],
                providers: []
            })
                .overrideTemplate(PcreleaseComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PcreleaseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PcreleaseService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Pcrelease(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.pcreleases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
