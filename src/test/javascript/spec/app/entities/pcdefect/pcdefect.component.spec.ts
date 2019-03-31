/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { PcdefectComponent } from 'app/entities/pcdefect/pcdefect.component';
import { PcdefectService } from 'app/entities/pcdefect/pcdefect.service';
import { Pcdefect } from 'app/shared/model/pcdefect.model';

describe('Component Tests', () => {
    describe('Pcdefect Management Component', () => {
        let comp: PcdefectComponent;
        let fixture: ComponentFixture<PcdefectComponent>;
        let service: PcdefectService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [PcdefectComponent],
                providers: []
            })
                .overrideTemplate(PcdefectComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PcdefectComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PcdefectService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Pcdefect(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.pcdefects[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
