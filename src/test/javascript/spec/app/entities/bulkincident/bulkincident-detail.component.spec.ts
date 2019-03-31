/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { BulkincidentDetailComponent } from 'app/entities/bulkincident/bulkincident-detail.component';
import { Bulkincident } from 'app/shared/model/bulkincident.model';

describe('Component Tests', () => {
    describe('Bulkincident Management Detail Component', () => {
        let comp: BulkincidentDetailComponent;
        let fixture: ComponentFixture<BulkincidentDetailComponent>;
        const route = ({ data: of({ bulkincident: new Bulkincident(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [BulkincidentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BulkincidentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BulkincidentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bulkincident).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
