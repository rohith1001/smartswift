/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { PctrackerDetailComponent } from 'app/entities/pctracker/pctracker-detail.component';
import { Pctracker } from 'app/shared/model/pctracker.model';

describe('Component Tests', () => {
    describe('Pctracker Management Detail Component', () => {
        let comp: PctrackerDetailComponent;
        let fixture: ComponentFixture<PctrackerDetailComponent>;
        const route = ({ data: of({ pctracker: new Pctracker(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [PctrackerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PctrackerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PctrackerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pctracker).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
