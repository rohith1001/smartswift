/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { IiaComponent } from 'app/entities/iia/iia.component';
import { IiaService } from 'app/entities/iia/iia.service';
import { Iia } from 'app/shared/model/iia.model';

describe('Component Tests', () => {
    describe('Iia Management Component', () => {
        let comp: IiaComponent;
        let fixture: ComponentFixture<IiaComponent>;
        let service: IiaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [IiaComponent],
                providers: []
            })
                .overrideTemplate(IiaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IiaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IiaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Iia(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.iias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
