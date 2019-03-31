/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { HldComponent } from 'app/entities/hld/hld.component';
import { HldService } from 'app/entities/hld/hld.service';
import { Hld } from 'app/shared/model/hld.model';

describe('Component Tests', () => {
    describe('Hld Management Component', () => {
        let comp: HldComponent;
        let fixture: ComponentFixture<HldComponent>;
        let service: HldService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [HldComponent],
                providers: []
            })
                .overrideTemplate(HldComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HldComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HldService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Hld(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.hlds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
