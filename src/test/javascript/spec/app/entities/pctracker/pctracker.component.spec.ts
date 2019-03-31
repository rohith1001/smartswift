/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { PctrackerComponent } from 'app/entities/pctracker/pctracker.component';
import { PctrackerService } from 'app/entities/pctracker/pctracker.service';
import { Pctracker } from 'app/shared/model/pctracker.model';

describe('Component Tests', () => {
    describe('Pctracker Management Component', () => {
        let comp: PctrackerComponent;
        let fixture: ComponentFixture<PctrackerComponent>;
        let service: PctrackerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [PctrackerComponent],
                providers: []
            })
                .overrideTemplate(PctrackerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PctrackerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PctrackerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Pctracker(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.pctrackers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
