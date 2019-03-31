/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { StkComponent } from 'app/entities/stk/stk.component';
import { StkService } from 'app/entities/stk/stk.service';
import { Stk } from 'app/shared/model/stk.model';

describe('Component Tests', () => {
    describe('Stk Management Component', () => {
        let comp: StkComponent;
        let fixture: ComponentFixture<StkComponent>;
        let service: StkService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [StkComponent],
                providers: []
            })
                .overrideTemplate(StkComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StkComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StkService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Stk(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.stks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
